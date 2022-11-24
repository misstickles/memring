package uk.co.jofaircloth.memring.ui.methodDisplay

import androidx.lifecycle.ViewModel
import uk.co.jofaircloth.memring.data.models.MethodItem
import uk.co.jofaircloth.memring.data.models.Stage
import uk.co.jofaircloth.memring.data.repository.MethodsCollectionRepository

class MethodDisplayViewModel : ViewModel() {
    private val sets = MethodsCollectionRepository.deserializeMethodCollection().methodSet

    fun selectMethodsForStage(stage: Int): List<MethodItem> {
        val stageType = Stage.values().associateBy(Stage::number)[stage]

        val methods = sets
            .filter { ms -> ms.properties.stage == stage }
//            .map { ms -> MethodItem(
//                stage = stage,
//                classification = Classification(
//                    isLittle = ms.properties.classification?.isLittle ?: false,
//                    isPlain = ms.properties.classification?.isPlain ?: false,
//                    isTrebleDodging = ms.properties.classification?.isTrebleDodging ?: false
//                ),
//                leadLength = ms.properties.lengthOfLead ?: 0,
//                huntBellPath = ms.properties.huntbellPath ?: "",
//                huntBells = ms.properties.numberOfHunts ?: 0
//            )}
            .flatMap { ms -> ms.method }
            .map { m -> MethodItem(
                id = m.id,
                stage = stageType,
                name = m.title ?: "",
                notation = m.notation ?: "",
            )}

        return methods
    }
}