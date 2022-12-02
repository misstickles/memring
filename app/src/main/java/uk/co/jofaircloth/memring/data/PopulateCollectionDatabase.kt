package uk.co.jofaircloth.memring.data

import android.util.Log
import uk.co.jofaircloth.memring.data.dao.MethodDao
import uk.co.jofaircloth.memring.data.dao.PerformanceDao
import uk.co.jofaircloth.memring.data.dao.PropertyDao
import uk.co.jofaircloth.memring.data.entities.MethodEntity
import uk.co.jofaircloth.memring.data.entities.PerformanceEntity
import uk.co.jofaircloth.memring.data.entities.PropertyEntity
import uk.co.jofaircloth.memring.data.xml.MethodsCollectionRepository

private const val TAG = "PrePopulateCollection"

suspend fun populateCollectionDatabase(
    methodDao: MethodDao,
    performanceDao: PerformanceDao,
    propertyDao: PropertyDao
) {
    try {
        Log.d(TAG, "TRYING populate")

        val collection = MethodsCollectionRepository().deserializeCollection()

        for ((idx, ms) in (collection.methodSet ?: listOf()).withIndex()) {
            val properties = ms.properties
            val property = PropertyEntity(
                id = idx,
                stage = ms.properties?.stage,
                classification = properties?.classification?.value,
                isDifferential = properties?.classification?.isDifferential ?: false,
                isPlain = properties?.classification?.isPlain ?: false,
                isLittle = properties?.classification?.isLittle ?: false,
                isTrebleDodging = properties?.classification?.isTrebleDodging ?: false,
                huntbellPath = properties?.huntbellPath,
                lengthOfLead = properties?.lengthOfLead,
                numberOfHunts = properties?.numberOfHunts
            )

            propertyDao.insert(property)

            for (m in ms.methods ?: listOf()) {
                val method = MethodEntity(
                    id = m.id,
                    propertyId = idx,
                    title = m.title,
                    name = m.name,
                    notation = m.notation,
                    leadHeadCode = m.leadHeadCode,
                    leadHead = m.leadHead,
                    symmetry = m.symmetry,
                    notes = m.notes,
                    extensionConstruction = m.extensionConstruction,
                    performanceId = 0,
                    fchGroups = m.falsness?.fchGroups,
                    rwReference = m.rwReference
                )

                methodDao.insert(method)
            }
        }

        Log.d(TAG, "DONE")
    } catch (e:java.lang.Exception) {
        Log.d(TAG, "$e")
    }
}