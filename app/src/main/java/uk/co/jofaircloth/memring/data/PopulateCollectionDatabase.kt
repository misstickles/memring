package uk.co.jofaircloth.memring.data

import android.util.Log
import uk.co.jofaircloth.memring.data.dao.MethodDao
import uk.co.jofaircloth.memring.data.dao.MethodPerformanceDao
import uk.co.jofaircloth.memring.data.dao.PerformanceDao
import uk.co.jofaircloth.memring.data.dao.PropertyDao
import uk.co.jofaircloth.memring.data.entities.MethodEntity
import uk.co.jofaircloth.memring.data.entities.MethodPerformanceEntity
import uk.co.jofaircloth.memring.data.entities.PerformanceEntity
import uk.co.jofaircloth.memring.data.entities.PropertyEntity
import uk.co.jofaircloth.memring.data.xml.MethodsCollectionRepository

private const val TAG = "PrePopulateCollection"

suspend fun populateCollectionDatabase(
    methodDao: MethodDao,
    performanceDao: PerformanceDao,
    propertyDao: PropertyDao,
    methodPerformanceDao: MethodPerformanceDao
) {
    try {
        Log.d(TAG, "POPULATING database")

        val collection = MethodsCollectionRepository().deserializeCollection()

        for (ms in collection.methodSet ?: listOf()) {
            val properties = ms.properties
            val property = PropertyEntity(
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

            val propertyId = propertyDao.insert(property)

            var performanceId: Long = -1

            for (m in ms.methods ?: listOf()) {
                val method = MethodEntity(
                    id = m.id,
                    propertyId = propertyId.toInt(),
                    title = m.title,
                    name = m.name,
                    notation = m.notation,
                    leadHeadCode = m.leadHeadCode,
                    leadHead = m.leadHead,
                    symmetry = m.symmetry,
                    notes = m.notes,
                    extensionConstruction = m.extensionConstruction,
                    fchGroups = m.falsness?.fchGroups,
                    rwReference = m.rwReference
                )

                methodDao.insert(method)

                // TODO When I added this as a lookup table - insert takes a lot longer...
                if (m.performances != null) {
                    val perf = m.performances

                    if (perf?.firstHandbellPeal != null) {
                        val first = perf?.firstHandbellPeal

                        val handPerformance = PerformanceEntity(
                            date = first?.date,
                            building = first?.location?.building,
                            county = first?.location?.county,
                            society = first?.society,
                            town = first?.location?.town,
                            type = "HAND"
                        )

                        performanceId = performanceDao.insert(handPerformance)
                        methodPerformanceDao.insert(
                            MethodPerformanceEntity(
                                performanceId = performanceId.toInt(),
                                methodId = m.id
                            )
                        )
                    }
                    if (perf?.firstTowerbellPeal != null) {
                        val first = perf?.firstTowerbellPeal

                        val towerPerformance = PerformanceEntity(
                            date = first?.date,
                            building = first?.location?.building,
                            county = first?.location?.county,
                            society = first?.society,
                            town = first?.location?.town,
                            type = "TOWER"
                        )

                        performanceId = performanceDao.insert(towerPerformance)
                        methodPerformanceDao.insert(
                            MethodPerformanceEntity(
                                performanceId = performanceId.toInt(),
                                methodId = m.id
                            )
                        )
                    }
                }
            }
        }

        Log.d(TAG, "DONE")
    } catch (e:java.lang.Exception) {
        Log.d(TAG, "$e")
    }
}