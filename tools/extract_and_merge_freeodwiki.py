#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
从 FreeODwiki 项目提取全部药物条目，精准去重与消歧，构建权威中英药典词典，
并深度合并到 PsychonautWiki Journal 项目的数据库 (Substances.json)、
本地化映射辅助类 (SubstanceLocalizationHelper.kt) 与词典交付物中。
"""

import os
import sys
import glob
import re
import json
from collections import OrderedDict
from pypinyin import lazy_pinyin

WIKI_DIR = "work/FreeODwiki"
DRUGS_DIR = os.path.join(WIKI_DIR, "药物")
INDEX_FILE = os.path.join(WIKI_DIR, "文档", "药物分类", "药物全索引.md")

APP_SUBSTANCES_JSON = "app/src/main/assets/Substances.json"
APP_ORIGINAL_JSON = "app/src/main/assets/Substances.original.json"
APP_DICT_ASSETS_JSON = "app/src/main/assets/substances_zh_dict.json"
WORK_DICT_JSON = "work/substances_zh_dict.json"
OUTPUT_DICT_JSON = "outputs/substances_zh_dict.json"
HELPER_KT = "app/src/main/java/com/isaakhanimann/journal/ui/tabs/search/SubstanceLocalizationHelper.kt"

CATEGORY_MAP = {
    "抑制剂": ("depressant", "中枢抑制剂"),
    "兴奋剂": ("stimulant", "中枢兴奋剂"),
    "解离剂": ("dissociative", "解离剂"),
    "迷幻剂": ("psychedelic", "致幻剂"),
    "致幻剂": ("psychedelic", "致幻剂"),
    "谵妄剂": ("deliriant", "谵妄剂"),
    "共情剂": ("entactogen", "共情剂"),
    "益智药": ("nootropic", "促智药"),
    "促智药": ("nootropic", "促智药"),
    "促醒剂": ("eugeroic", "促醒剂"),
    "大麻类": ("cannabinoid", "大麻素类"),
    "植物大麻素": ("cannabinoid", "大麻素类"),
    "合成大麻素类物质": ("cannabinoid", "大麻素类"),
    "苯二氮䓬类": ("benzodiazepine", "苯二氮䓬类"),
    "苯二氮䓬类物质": ("benzodiazepine", "苯二氮䓬类"),
    "巴比妥类": ("barbiturate", "巴比妥类"),
    "巴比妥类物质": ("barbiturate", "巴比妥类"),
    "阿片类": ("opioid", "阿片类"),
    "阿片类药物": ("opioid", "阿片类"),
    "促梦剂": ("oneirogen", "催梦剂"),
    "抗精神病药": ("antipsychotic", "抗精神病药"),
    "抗抑郁药": ("antidepressant", "抗抑郁药"),
    "吸入剂": ("hypnotic", "吸入麻醉/催眠"),
}

ROA_MAP = {
    "口服": "oral",
    "直肠": "rectal",
    "直肠给药": "rectal",
    "舌下": "sublingual",
    "颊黏膜": "buccal",
    "鼻吸": "insufflated",
    "鼻腔": "insufflated",
    "抽吸": "smoked",
    "吸入": "inhaled",
    "静脉": "intravenous",
    "静脉注射": "intravenous",
    "肌注": "intramuscular",
    "肌肉注射": "intramuscular",
    "皮下": "subcutaneous",
    "透皮": "transdermal",
}

EXPLICIT_ZH_TO_EN = {
    "奥氮平": "Olanzapine",
    "氯氮平": "Clozapine",
    "金刚烷胺": "Amantadine",
    "丙戊酸": "Valproic acid",
    "丙戊酸盐": "Sodium valproate",
    "反苯环丙胺": "Tranylcypromine",
    "二氯地西泮": "Diclazepam",
    "吡溴唑仑": "Pyrazolam",
    "去氯依替唑仑": "Deschloroetizolam",
    "氟氯替唑仑": "Fluclotizolam",
    "普罗斯卡林": "Proscaline",
    "替扎尼定": "Tizanidine",
    "毒蝇伞": "Amanita muscaria",
    "育亨宾": "Yohimbine",
    "胍丁胺": "Agmatine",
    "茶苯海明": "Dimenhydrinate",
    "苯海索": "Trihexyphenidyl",
    "阿托品": "Atropine",
    "鹅膏蕈氨酸": "Ibotenic acid",
    "圣佩德罗仙人掌": "San Pedro cactus",
    "秘鲁火炬仙人掌": "Peruvian torch cactus",
    "玻利维亚火炬仙人掌": "Bolivian torch cactus",
    "复方甘草片": "Compound Licorice Tablets",
    "愈美片": "Yumei Tablets",
    "哮喘片": "Asthma Tablets",
    "水虉草": "Phalaris aquatica",
    "虉草属": "Phalaris",
    "芦竹": "Arundo donax",
    "露子花属": "Aptenia",
    "鹅花树": "Tabernaemontana",
    "曼陀罗属": "Datura",
    "曼陀罗": "Datura stramonium",
    "烟草": "Tobacco",
    "香烟": "Tobacco",
    "镁剂": "Magnesium",
}

def clean_wiki_text(text):
    if not text:
        return ""
    text = re.sub(r'\[([^\]]+)\]\([^\)]+\)', r'\1', text)
    text = re.sub(r'[*_`]', '', text)
    text = re.sub(r'<[^>]+>', '', text)
    text = re.sub(r'\[\^\d+\]', '', text)
    return text.strip()

def contains_chinese(text):
    return bool(re.search(r'[\u4e00-\u9fa5]', text))

def generate_pinyin_tokens(chinese_text):
    if not chinese_text or not contains_chinese(chinese_text):
        return [], []
    tokens = lazy_pinyin(chinese_text)
    if not tokens:
        return [], []
    abbr = "".join([t[0] for t in tokens if t])
    full_clean = "".join(tokens)
    full_apostrophe = "'".join(tokens)
    pinyins = [full_apostrophe, full_clean]
    abbrs = [abbr] if abbr else []
    return pinyins, abbrs

def split_aliases(raw_str):
    if not raw_str:
        return []
    raw_str = clean_wiki_text(raw_str)
    # 提取括号内部作为别名
    parentheticals = re.findall(r'[（\(]([^）\)]+)[）\)]', raw_str)
    # 移除括号本身，保留主体
    clean_outer = re.sub(r'[（\(][^）\)]+[）\)]', '', raw_str)
    items = []
    for token in re.split(r'(?<!\d),(?!\d)|[，/、;；]', clean_outer):
        token = clean_wiki_text(token)
        if token and not any(noise in token for noise in ['商品名', '注意', '植物学', '参见', '原研']):
            items.append(token)
    for p in parentheticals:
        for sub in re.split(r'[,，/、;；\s]', p):
            sub = clean_wiki_text(sub)
            if sub and not any(noise in sub for noise in ['商品名', '注意', '植物学', '参见', '原研']):
                items.append(sub)
    return list(dict.fromkeys(items))

def parse_number_range(text):
    if not text:
        return None, None, "mg"
    text = text.replace("\\~", "~").replace("&plus;", "+")
    units = "mg"
    if "μg" in text or "ug" in text or "mcg" in text:
        units = "μg"
    elif "ml" in text or "mL" in text:
        units = "mL"
    elif "g" in text and "mg" not in text and "μg" not in text and "ug" not in text:
        units = "g"

    nums = re.findall(r'(\d+(?:\.\d+)?)', text)
    if not nums:
        return None, None, units
    if len(nums) == 1:
        return float(nums[0]), None, units
    return float(nums[0]), float(nums[1]), units

def parse_duration_range(text):
    if not text:
        return None, None, "hours"
    text = text.replace("\\~", "~")
    units = "hours"
    if "分" in text or "min" in text.lower():
        units = "minutes"
    elif "秒" in text or "sec" in text.lower():
        units = "seconds"
    elif "天" in text or "day" in text.lower():
        units = "days"

    nums = re.findall(r'(\d+(?:\.\d+)?)', text)
    if not nums:
        return None, None, units
    if len(nums) == 1:
        return float(nums[0]), None, units
    return float(nums[0]), float(nums[1]), units

def parse_index_file():
    if not os.path.exists(INDEX_FILE):
        return {}
    with open(INDEX_FILE, "r", encoding="utf-8") as f:
        lines = f.readlines()

    curr_cat = None
    curr_subcat = None
    mapping = {}

    for line in lines:
        line = line.strip()
        m_h3 = re.match(r'^###\s+\[?([^\]\(\)\n]+)\]?', line)
        if m_h3:
            curr_cat = clean_wiki_text(m_h3.group(1))
            curr_subcat = None
            continue
        m_h4 = re.match(r'^####\s+\[?([^\]\(\)\n]+)\]?', line)
        if m_h4:
            curr_subcat = clean_wiki_text(m_h4.group(1))
            continue
        
        m_item = re.match(r'^\*+\s+\[([^\]]+)\]\(([^)]+)\)(.*)$', line)
        if m_item:
            title = clean_wiki_text(m_item.group(1))
            link = m_item.group(2).strip()
            extra = m_item.group(3).strip()
            extra_names = split_aliases(extra)
            base_fn = os.path.basename(link)
            if base_fn.endswith(".md"):
                base_name = base_fn[:-3]
                mapping[base_name.lower()] = {
                    "index_title": title,
                    "index_cat": curr_cat,
                    "index_subcat": curr_subcat,
                    "extra_names": extra_names
                }
    return mapping

def parse_freeodwiki_drug_file(fpath, index_map):
    fname = os.path.basename(fpath)[:-3]
    fname_clean = re.sub(r'^讨论[：:]\s*', '', fname).strip()
    with open(fpath, "r", encoding="utf-8", errors="ignore") as f:
        content = f.read()

    # 桩页面/重定向处理
    is_stub = False
    clean_content = content.strip()
    if len(clean_content) < 180 and any(kw in clean_content for kw in ['见[', '参见[', '请见[', '另见[', '施工中']):
        is_stub = True

    # 1. 标题与前缀清洗
    m_h1 = re.search(r'^#\s+(.+)$', content, re.MULTILINE)
    title = clean_wiki_text(m_h1.group(1)) if m_h1 else fname_clean
    title = re.sub(r'^讨论[：:]\s*', '', title).strip()

    # 提取括号内部作为别名 (如 "茶苯海明（Dimenhydrinate）", "曼陀罗（植物学）")
    bracket_m = re.findall(r'[（\(]([^）\)]+)[）\)]', title)
    title = re.sub(r'[（\(][^）\)]+[）\)]', '', title).strip()
    if not title:
        title = fname_clean

    index_info = index_map.get(fname.lower(), {}) or index_map.get(fname_clean.lower(), {})
    index_title = index_info.get("index_title", "")
    extra_names = index_info.get("extra_names", [])

    # 2. 提取表格信息
    chem_info = {}
    table_m = re.findall(r'\|\s*([^\|\r\n]+?)\s*\|\s*([^\|\r\n]+?)\s*\|', content)
    for k, v in table_m:
        k_clean = re.sub(r'[*_`\s]', '', k)
        v_clean = clean_wiki_text(v)
        if k_clean and v_clean and k_clean not in chem_info:
            chem_info[k_clean] = v_clean

    cas = chem_info.get("CAS号") or chem_info.get("CAS")
    if not cas:
        m_cas = re.search(r'CAS\s*号?\s*\|\s*([0-9\-]+)', content)
        if m_cas:
            cas = m_cas.group(1).strip()

    # 提取化学结构图中的英文名 (如 /文件/Olanzapine.png)
    imgs = re.findall(r'/文件/([a-zA-Z0-9_\-]+)\.(?:png|svg|jpg)', content)
    chem_img_name = None
    for img in imgs:
        if not any(x in img.lower() for x in ['skull', 'crossbones', 'warning', 'logo', 'icon', 'arrow', 'banner', 'button', 'child', 'category', 'commons', 'eye', 'leaf', 'placeholder', 'molecule']) and len(img) > 2:
            clean_img = re.sub(r'(_?[Ss]tructure|_metabolic_Pathways|Metacogghjgjvghnition)', '', img).strip('_')
            if clean_img and not contains_chinese(clean_img):
                chem_img_name = clean_img
                break

    # 3. 别名与常用名解析
    zh_aliases = []
    en_aliases = []
    
    # 将括号内提取的名词分类放入
    for b in bracket_m:
        b_clean = clean_wiki_text(b)
        if any(noise in b_clean for noise in ['植物学', '注意', '待定', '讨论']):
            continue
        for sub in split_aliases(b_clean):
            if contains_chinese(sub):
                if sub not in zh_aliases: zh_aliases.append(sub)
            else:
                if sub not in en_aliases: en_aliases.append(sub)

    if chem_img_name and chem_img_name not in en_aliases:
        en_aliases.append(chem_img_name)

    raw_commons = chem_info.get("常用名") or chem_info.get("常见名称") or chem_info.get("常用名称") or chem_info.get("别名") or ""
    all_raw_tokens = split_aliases(raw_commons)
    
    sub_name = chem_info.get("取代名称")
    if sub_name:
        all_raw_tokens.extend(split_aliases(sub_name))

    for token in all_raw_tokens + extra_names:
        if contains_chinese(token):
            if token not in zh_aliases: zh_aliases.append(token)
        else:
            if token not in en_aliases: en_aliases.append(token)

    # 4. 判定标准英文名与中文名
    name_zh = ""
    name_en = ""

    if contains_chinese(title):
        name_zh = title
        if title in EXPLICIT_ZH_TO_EN:
            name_en = EXPLICIT_ZH_TO_EN[title]
        elif fname_clean in EXPLICIT_ZH_TO_EN:
            name_en = EXPLICIT_ZH_TO_EN[fname_clean]
        elif chem_img_name:
            name_en = chem_img_name
        elif en_aliases:
            name_en = en_aliases[0]
        else:
            name_en = fname_clean if not contains_chinese(fname_clean) else ""
    else:
        name_en = title
        if index_title and contains_chinese(index_title):
            name_zh = re.sub(r'[（\(][^）\)]+[）\)]', '', index_title).strip()
        elif zh_aliases:
            name_zh = zh_aliases[0]
        else:
            name_zh = name_en

    # 清洗中文名可能残留的括号
    name_zh = re.sub(r'[（\(][^）\)]+[）\)]', '', name_zh).strip()
    if not name_zh:
        name_zh = title

    # 5. 分类映射
    raw_psy = chem_info.get("精神活性类别") or chem_info.get("精神活性分类") or chem_info.get("精神药效类别") or index_info.get("index_cat") or ""
    raw_chem = chem_info.get("化学类别") or chem_info.get("化学分类") or index_info.get("index_subcat") or ""
    
    cat_keys = []
    cat_zh_list = []

    for raw in [raw_psy, raw_chem]:
        if not raw: continue
        for piece in re.split(r'[,，/、\s]', raw):
            piece = clean_wiki_text(piece)
            if piece in CATEGORY_MAP:
                k, zh = CATEGORY_MAP[piece]
                if k not in cat_keys: cat_keys.append(k)
                if zh not in cat_zh_list: cat_zh_list.append(zh)

    if not cat_keys:
        cat_keys.append("research-chemical")
        cat_zh_list.append("研究性化学品")

    # 6. 解析 ROAs
    roas = []
    lines = content.split('\n')
    roa_headers = []
    in_roa_table = False
    roa_rows = []

    for l in lines:
        l_str = l.strip()
        if "给药途径" in l_str and "|" in l_str:
            in_roa_table = True
            cols = [clean_wiki_text(c) for c in l_str.split("|")[1:-1]]
            roa_headers = cols[1:]
            continue
        if in_roa_table:
            if l_str.startswith("|") and l_str.endswith("|"):
                row_cols = [clean_wiki_text(c) for c in l_str.split("|")[1:-1]]
                if row_cols and not re.match(r'^[\-:\s]+$', row_cols[0]):
                    roa_rows.append(row_cols)
            else:
                if len(roa_rows) > 2:
                    break
                else:
                    in_roa_table = False
                    roa_headers = []
                    roa_rows = []

    if roa_headers and roa_rows:
        col_count = len(roa_headers)
        for col_idx in range(col_count):
            raw_route = roa_headers[col_idx]
            route_name = ROA_MAP.get(raw_route, "oral")
            dose_data = {}
            dur_data = {}

            for row in roa_rows:
                if not row or len(row) <= col_idx + 1:
                    continue
                label = row[0]
                val = row[col_idx + 1]
                if not val:
                    continue

                if "阈值" in label:
                    n_min, _, u = parse_number_range(val)
                    if n_min is not None:
                        dose_data["units"] = u
                        dose_data["threshold"] = n_min
                elif "轻微" in label:
                    n_min, _, u = parse_number_range(val)
                    if n_min is not None:
                        dose_data["units"] = u
                        dose_data["lightMin"] = n_min
                elif "中等" in label:
                    n_min, _, u = parse_number_range(val)
                    if n_min is not None:
                        dose_data["units"] = u
                        dose_data["commonMin"] = n_min
                elif "强烈" in label:
                    n_min, _, u = parse_number_range(val)
                    if n_min is not None:
                        dose_data["units"] = u
                        dose_data["strongMin"] = n_min
                elif "严重" in label:
                    n_min, _, u = parse_number_range(val)
                    if n_min is not None:
                        dose_data["units"] = u
                        dose_data["heavyMin"] = n_min

                if "发作" in label:
                    d_min, d_max, u = parse_duration_range(val)
                    if d_min is not None:
                        dur_data["onset"] = {"min": d_min, "max": d_max or d_min, "units": u}
                elif "上升" in label:
                    d_min, d_max, u = parse_duration_range(val)
                    if d_min is not None:
                        dur_data["comeup"] = {"min": d_min, "max": d_max or d_min, "units": u}
                elif "达峰" in label or "峰值" in label:
                    d_min, d_max, u = parse_duration_range(val)
                    if d_min is not None:
                        dur_data["peak"] = {"min": d_min, "max": d_max or d_min, "units": u}
                elif "褪去" in label:
                    d_min, d_max, u = parse_duration_range(val)
                    if d_min is not None:
                        dur_data["offset"] = {"min": d_min, "max": d_max or d_min, "units": u}
                elif "总时长" in label:
                    d_min, d_max, u = parse_duration_range(val)
                    if d_min is not None:
                        dur_data["total"] = {"min": d_min, "max": d_max or d_min, "units": u}
                elif "残余" in label or "余波" in label:
                    d_min, d_max, u = parse_duration_range(val)
                    if d_min is not None:
                        dur_data["afterglow"] = {"min": d_min, "max": d_max or d_min, "units": u}

            roa_item = {"name": route_name}
            if dose_data:
                roa_item["dose"] = dose_data
            if dur_data:
                roa_item["duration"] = dur_data
            if dose_data or dur_data:
                roas.append(roa_item)

    # 7. 提取相互作用
    interactions = {"dangerous": [], "unsafe": [], "uncertain": []}
    m_warn = re.search(r'危险的.*相互作用[：:]\s*([^\n\r]+)', content)
    if m_warn:
        raw_warn = clean_wiki_text(m_warn.group(1))
        for item in re.split(r'[,，、/]', raw_warn):
            item = item.strip()
            if item and item not in interactions["dangerous"]:
                interactions["dangerous"].append(item)

    # 8. 摘要
    paras = [p.strip() for p in content.split('\n\n') if p.strip()]
    summary = ""
    for p in paras:
        if p.startswith("#") or p.startswith("---") or p.startswith("|") or p.startswith(">") or p.startswith("!["):
            continue
        if "<details" in p or "</details>" in p:
            continue
        cleaned = clean_wiki_text(p)
        if len(cleaned) > 25 and not cleaned.startswith("◀返回"):
            summary = cleaned
            break

    return {
        "filename": fname,
        "name_en": name_en,
        "name_zh": name_zh,
        "zh_aliases": zh_aliases,
        "en_aliases": en_aliases,
        "cas": cas,
        "categories": cat_keys,
        "categories_zh": cat_zh_list,
        "summary": summary,
        "roas": roas,
        "interactions": interactions,
        "is_stub": is_stub,
        "url": f"https://github.com/SalviaSWC/FreeODwiki/blob/main/药物/{fname}.md"
    }

def main():
    print("==================================================")
    print(" 🚀 开始严格消歧版 FreeODwiki 药物提取与项目合并")
    print("==================================================")

    # 1. 载入既有词典
    with open(WORK_DICT_JSON, "r", encoding="utf-8") as f:
        base_dict = json.load(f)
    existing_dict_subs = base_dict.get("substances", {})

    # 清除历史遗留的拼音代号或非规范映射
    cleaned_dict_subs = {}
    for k, v in existing_dict_subs.items():
        zh = v.get("name_zh")
        if zh in EXPLICIT_ZH_TO_EN and k != EXPLICIT_ZH_TO_EN[zh]:
            continue
        if k in ['Midantan', 'Erlvdixipan', 'Qulvyitizuolun', 'Bixiuzuolun', 'Aodanping', 'Fulvtizuolun', 'Lvdanping']:
            continue
        cleaned_dict_subs[k] = v
    existing_dict_subs = cleaned_dict_subs

    # 建立中文到权威英文名的索引
    zh_to_canonical = {}
    for k, v in existing_dict_subs.items():
        canonical_en = v.get("name_en") or k
        if v.get("name_zh"):
            zh_to_canonical[v["name_zh"]] = canonical_en
        for a in v.get("zh_aliases", []):
            zh_to_canonical[a] = canonical_en

    # 2. 载入原始 App Substances 数据库作为权威标准底本
    with open(APP_ORIGINAL_JSON, "r", encoding="utf-8") as f:
        orig_app_data = json.load(f)
    orig_substances = orig_app_data.get("substances", [])
    
    # 建立原版 App 物质名索引 (大小写无关)
    orig_name_map = {s["name"].lower(): s for s in orig_substances}
    orig_name_exact = {s["name"].lower(): s["name"] for s in orig_substances}

    # 3. 解析 FreeODwiki
    index_map = parse_index_file()
    wiki_files = glob.glob(os.path.join(DRUGS_DIR, "*.md"))
    wiki_files = [f for f in wiki_files if not f.endswith("index.md")]

    parsed_wiki_drugs = []
    for fpath in wiki_files:
        info = parse_freeodwiki_drug_file(fpath, index_map)
        parsed_wiki_drugs.append(info)

    print(f"[*] 解析完成: 共 {len(parsed_wiki_drugs)} 篇 FreeODwiki 药物文档")

    # 4. 精准匹配与消歧
    merged_substances_map = {}
    for s in orig_substances:
        merged_substances_map[s["name"].lower()] = json.loads(json.dumps(s))

    # 统计指标
    enriched_count = 0
    new_substances_count = 0

    # 综合字典构建
    master_dict = OrderedDict()
    for k, v in existing_dict_subs.items():
        master_dict[k] = json.loads(json.dumps(v))

    for item in parsed_wiki_drugs:
        fn = item["filename"]
        n_zh = item["name_zh"]
        n_en = item["name_en"]
        en_aliases = item["en_aliases"]
        zh_aliases = item["zh_aliases"]
        is_stub = item.get("is_stub", False)

        # 查找权威英文名
        matched_canonical = None

        # 0. 优先查显式标准中英映射 (最高优先级权威消歧)
        if n_zh in EXPLICIT_ZH_TO_EN:
            matched_canonical = EXPLICIT_ZH_TO_EN[n_zh]
        elif fn in EXPLICIT_ZH_TO_EN:
            matched_canonical = EXPLICIT_ZH_TO_EN[fn]
        elif n_en in EXPLICIT_ZH_TO_EN.values():
            matched_canonical = n_en

        # A. 查已有原版物质名
        if not matched_canonical:
            if fn.lower() in orig_name_exact:
                matched_canonical = orig_name_exact[fn.lower()]
            elif n_en and n_en.lower() in orig_name_exact:
                matched_canonical = orig_name_exact[n_en.lower()]
            else:
                for alias in en_aliases:
                    if alias.lower() in orig_name_exact:
                        matched_canonical = orig_name_exact[alias.lower()]
                        break

        # B. 查已合并物质表
        if not matched_canonical:
            if n_en and n_en.lower() in merged_substances_map:
                matched_canonical = merged_substances_map[n_en.lower()]["name"]

        # C. 查中文名映射
        if not matched_canonical:
            if n_zh in zh_to_canonical:
                matched_canonical = zh_to_canonical[n_zh]
            elif fn in zh_to_canonical:
                matched_canonical = zh_to_canonical[fn]

        if is_stub:
            if matched_canonical and matched_canonical.lower() in merged_substances_map:
                target = merged_substances_map[matched_canonical.lower()]
                for z in [n_zh, fn] + zh_aliases:
                    if z and z not in target["commonNames"]:
                        target["commonNames"].append(z)
            continue

        if matched_canonical and matched_canonical.lower() in merged_substances_map:
            # === 情况 1: 匹配到已有权威物质，执行深度增强，绝不重复生成 ===
            target = merged_substances_map[matched_canonical.lower()]
            
            # 补齐 ROAs (如果原版没有)
            if not target.get("roas") and item.get("roas"):
                target["roas"] = item["roas"]
            # 补齐相互作用禁忌
            if not target.get("interactions") and item.get("interactions"):
                target["interactions"] = item["interactions"]

            # 汇总中文名与别名
            cur_commons = list(target.get("commonNames") or [])
            all_zh_names = []
            if n_zh and n_zh not in all_zh_names: all_zh_names.append(n_zh)
            for a in zh_aliases:
                if a and a not in all_zh_names: all_zh_names.append(a)

            # 更新字典条目
            dict_key = matched_canonical
            if dict_key in master_dict:
                d_entry = master_dict[dict_key]
                if not d_entry.get("name_zh") or d_entry["name_zh"] == dict_key:
                    d_entry["name_zh"] = n_zh
                for a in all_zh_names:
                    if a and a not in d_entry.get("zh_aliases", []):
                        d_entry.setdefault("zh_aliases", []).append(a)
                if not d_entry.get("cas") and item.get("cas"):
                    d_entry["cas"] = item["cas"]
            else:
                master_dict[dict_key] = {
                    "name_en": dict_key,
                    "name_zh": n_zh,
                    "zh_aliases": [a for a in all_zh_names if a != n_zh],
                    "categories": item["categories"],
                    "categories_zh": item["categories_zh"],
                    "cas": item["cas"],
                    "url": item["url"]
                }

            # 注入拼音
            p_list = []
            ab_list = []
            for z in all_zh_names:
                pys, abs_ = generate_pinyin_tokens(z)
                for p in pys:
                    if p not in p_list: p_list.append(p)
                for a in abs_:
                    if a not in ab_list: ab_list.append(a)

            # 合并到 target["commonNames"]
            for z in all_zh_names:
                if z not in cur_commons: cur_commons.append(z)
            for en in en_aliases:
                if en and en != target["name"] and en not in cur_commons: cur_commons.append(en)
            for ab in ab_list:
                if ab not in cur_commons: cur_commons.append(ab)
            for py in p_list:
                if py not in cur_commons: cur_commons.append(py)

            target["commonNames"] = cur_commons
            enriched_count += 1

        else:
            # === 情况 2: 全新物质（来自 FreeODwiki 独家 RC/植物/复方） ===
            canonical_name = matched_canonical or n_en or fn
            # 避免纯汉字作为英文标识
            if contains_chinese(canonical_name):
                if canonical_name in EXPLICIT_ZH_TO_EN:
                    canonical_name = EXPLICIT_ZH_TO_EN[canonical_name]
                elif n_zh in EXPLICIT_ZH_TO_EN:
                    canonical_name = EXPLICIT_ZH_TO_EN[n_zh]
                elif fn in EXPLICIT_ZH_TO_EN:
                    canonical_name = EXPLICIT_ZH_TO_EN[fn]
                else:
                    pys, _ = generate_pinyin_tokens(canonical_name)
                    canonical_name = (pys[1] if len(pys) > 1 else canonical_name).capitalize()

            all_zh_names = []
            if n_zh and n_zh not in all_zh_names: all_zh_names.append(n_zh)
            for a in zh_aliases:
                if a and a not in all_zh_names: all_zh_names.append(a)

            p_list = []
            ab_list = []
            for z in all_zh_names:
                pys, abs_ = generate_pinyin_tokens(z)
                for p in pys:
                    if p not in p_list: p_list.append(p)
                for a in abs_:
                    if a not in ab_list: ab_list.append(a)

            common_names_list = []
            common_names_list.extend(all_zh_names)
            for en in en_aliases:
                if en and en != canonical_name and en not in common_names_list:
                    common_names_list.append(en)
            common_names_list.extend(ab_list)
            common_names_list.extend(p_list)

            new_sub_obj = {
                "name": canonical_name,
                "commonNames": common_names_list,
                "url": item["url"],
                "isApproved": True,
                "tolerance": None,
                "crossTolerances": [],
                "addictionPotential": None,
                "toxicities": [],
                "categories": item["categories"],
                "summary": item["summary"] or f"精神活性物质: {n_zh or canonical_name}",
                "interactions": item["interactions"],
                "roas": item["roas"]
            }

            merged_substances_map[canonical_name.lower()] = new_sub_obj
            new_substances_count += 1

            # 录入词典
            master_dict[canonical_name] = {
                "name_en": canonical_name,
                "name_zh": n_zh,
                "zh_aliases": [a for a in all_zh_names if a != n_zh],
                "pinyin": p_list,
                "pinyin_abbr": ab_list,
                "categories": item["categories"],
                "categories_zh": item["categories_zh"],
                "cas": item["cas"],
                "url": item["url"]
            }

    print(f"[+] 物质融合完毕: 增强已有物质 {enriched_count} 个，新增独立物质 {new_substances_count} 个，库内总物质数 {len(merged_substances_map)}")

    # 5. 补充全量拼音到字典
    for k, v in master_dict.items():
        all_zh = [v.get("name_zh")] + v.get("zh_aliases", [])
        all_zh = [z for z in all_zh if z and contains_chinese(z)]
        p_list = list(v.get("pinyin", []))
        ab_list = list(v.get("pinyin_abbr", []))
        for z in all_zh:
            pys, abs_ = generate_pinyin_tokens(z)
            for p in pys:
                if p not in p_list: p_list.append(p)
            for a in abs_:
                if a not in ab_list: ab_list.append(a)
        v["pinyin"] = p_list
        v["pinyin_abbr"] = ab_list

    # 6. 保存最终 App Substances.json
    final_substances_list = sorted(list(merged_substances_map.values()), key=lambda x: x["name"])
    final_app_output = {
        "categories": orig_app_data.get("categories", []),
        "substances": final_substances_list
    }
    with open(APP_SUBSTANCES_JSON, "w", encoding="utf-8") as f:
        json.dump(final_app_output, f, ensure_ascii=False, indent=2)

    # 7. 保存最终词典
    final_dict_output = {
        "$schema": "./substances_zh_dict.schema.json",
        "metadata": {
            "title": "PsychonautWiki Journal & FreeODwiki 综合中英文药典与拼音检索词典",
            "version": "2.2.0",
            "language": "zh-CN",
            "total_substances": len(master_dict),
            "total_app_substances": len(final_substances_list),
            "description": "经权威消歧清洗的中英文药典，包含商品名、民间黑话、拼音缩写及剂量时效全量参数。"
        },
        "categories": base_dict.get("categories", {}),
        "substances": master_dict
    }

    os.makedirs("work", exist_ok=True)
    os.makedirs("outputs", exist_ok=True)
    with open(WORK_DICT_JSON, "w", encoding="utf-8") as f:
        json.dump(final_dict_output, f, ensure_ascii=False, indent=2)
    with open(OUTPUT_DICT_JSON, "w", encoding="utf-8") as f:
        json.dump(final_dict_output, f, ensure_ascii=False, indent=2)
    with open(APP_DICT_ASSETS_JSON, "w", encoding="utf-8") as f:
        json.dump(final_dict_output, f, ensure_ascii=False, indent=2)

    # 8. 更新 Kotlin SubstanceLocalizationHelper.kt
    with open(HELPER_KT, "r", encoding="utf-8") as f:
        kt_code = f.read()

    zh_map_entries = []
    for k, v in master_dict.items():
        en = v.get("name_en") or k
        zh = v.get("name_zh")
        if en and zh and en != zh and contains_chinese(zh):
            clean_zh = re.sub(r'^讨论[：:]\s*', '', zh).strip()
            clean_zh = re.sub(r'[（\(][^）\)]+[）\)]', '', clean_zh).strip()
            clean_en = en.strip()
            if clean_zh and not contains_chinese(clean_en):
                zh_map_entries.append((clean_en, clean_zh))

    zh_map_entries = sorted(list(set(zh_map_entries)), key=lambda x: x[0].lower())

    map_lines = []
    for en, zh in zh_map_entries:
        escaped_en = en.replace('"', '\\"')
        escaped_zh = zh.replace('"', '\\"')
        map_lines.append(f'        "{escaped_en}" to "{escaped_zh}",')

    new_map_str = "    private val ZH_NAME_MAP: Map<String, String> = mapOf(\n" + "\n".join(map_lines) + "\n    )"
    
    kt_code_updated = re.sub(
        r'    private val ZH_NAME_MAP:\s*Map<String,\s*String>\s*=\s*mapOf\([\s\S]*?\n    \)',
        new_map_str,
        kt_code
    )

    with open(HELPER_KT, "w", encoding="utf-8") as f:
        f.write(kt_code_updated)

    print(f"[+] Kotlin 静态映射表更新完成: 共注入 {len(zh_map_entries)} 个标准中文名映射！")
    print("==================================================")
    print(" 🎉 严格消歧版药物提取与项目合并成功完成！")
    print("==================================================")

if __name__ == "__main__":
    main()
