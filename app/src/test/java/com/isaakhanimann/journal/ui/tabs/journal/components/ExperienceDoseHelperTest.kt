package com.isaakhanimann.journal.ui.tabs.journal.components

import com.isaakhanimann.journal.data.substances.AdministrationRoute
import com.isaakhanimann.journal.data.room.experiences.entities.Ingestion
import com.isaakhanimann.journal.data.room.experiences.relations.IngestionWithCompanionAndCustomUnit
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test
import java.time.Instant

class ExperienceDoseHelperTest {

    private fun createIngestionWith(
        substanceName: String,
        dose: Double?,
        units: String? = "mg",
        isEstimate: Boolean = false,
        sd: Double? = null,
        consumerName: String? = null
    ): IngestionWithCompanionAndCustomUnit {
        val ingestion = Ingestion(
            id = 1,
            substanceName = substanceName,
            time = Instant.now(),
            administrationRoute = AdministrationRoute.ORAL,
            dose = dose,
            isDoseAnEstimate = isEstimate,
            estimatedDoseStandardDeviation = sd,
            units = units,
            experienceId = 1,
            notes = null,
            stomachFullness = null,
            consumerName = consumerName,
            customUnitId = null
        )
        return IngestionWithCompanionAndCustomUnit(
            ingestion = ingestion,
            substanceCompanion = null,
            customUnit = null
        )
    }

    @Test
    fun testEmptyIngestions() {
        val result = ExperienceDoseHelper.formatExperienceDose(emptyList())
        assertNull(result)
    }

    @Test
    fun testSingleIngestionNormal() {
        val list = listOf(
            createIngestionWith("Clonazepam", 1.0, "mg")
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("1 mg", result)
    }

    @Test
    fun testSingleIngestionDecimal() {
        val list = listOf(
            createIngestionWith("Clonazepam", 0.25, "mg")
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("0.25 mg", result)
    }

    @Test
    fun testSameSubstanceRedoseAccumulation() {
        // 用户短时间内多次给药：在药效内再次追加，累加在一起
        // 第一次 150 mg，第二次 75 mg -> 225 mg
        val list = listOf(
            createIngestionWith("Pregabalin", 150.0, "mg"),
            createIngestionWith("Pregabalin", 75.0, "mg")
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("225 mg", result)
    }

    @Test
    fun testTripleRedoseAccumulation() {
        // 三次追加：50mg + 25mg + 25mg = 100mg
        val list = listOf(
            createIngestionWith("Amantadine", 50.0, "mg"),
            createIngestionWith("Amantadine", 25.0, "mg"),
            createIngestionWith("Amantadine", 25.0, "mg")
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("100 mg", result)
    }

    @Test
    fun testRedoseWithEstimate() {
        // 其中一次为估算：100 mg + ~50 mg -> ~150 mg
        val list = listOf(
            createIngestionWith("Pregabalin", 100.0, "mg", isEstimate = false),
            createIngestionWith("Pregabalin", 50.0, "mg", isEstimate = true)
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("~150 mg", result)
    }

    @Test
    fun testRedoseWithDeviation() {
        // 带有标准差：100±10 mg + 50±5 mg -> 150±15 mg
        val list = listOf(
            createIngestionWith("Pregabalin", 100.0, "mg", isEstimate = true, sd = 10.0),
            createIngestionWith("Pregabalin", 50.0, "mg", isEstimate = true, sd = 5.0)
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("150±15 mg", result)
    }

    @Test
    fun testRedoseWithOneNullDose() {
        // 第一次 100 mg，第二次未记录剂量 -> ~100 mg
        val list = listOf(
            createIngestionWith("Pregabalin", 100.0, "mg"),
            createIngestionWith("Pregabalin", null, "mg")
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("~100 mg", result)
    }

    @Test
    fun testPolysubstance() {
        // 多种药物：普瑞巴林 150 mg + 氯硝西泮 1.0 mg
        val list = listOf(
            createIngestionWith("Pregabalin", 150.0, "mg"),
            createIngestionWith("Clonazepam", 1.0, "mg")
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("150 mg + 1 mg", result)
    }

    @Test
    fun testPolysubstanceWithRedose() {
        // 普瑞巴林 150 mg + 追加 75 mg，以及氯硝西泮 1.0 mg
        val list = listOf(
            createIngestionWith("Pregabalin", 150.0, "mg"),
            createIngestionWith("Clonazepam", 1.0, "mg"),
            createIngestionWith("Pregabalin", 75.0, "mg")
        )
        val result = ExperienceDoseHelper.formatExperienceDose(list)
        assertEquals("225 mg + 1 mg", result)
    }
}
