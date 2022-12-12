package uk.co.jofaircloth.memring.data.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.co.jofaircloth.memring.data.entities.MethodPropertyEntity

@Dao
abstract class MethodPropertyDao {
    // TODO nullable stage
    @Query(
        """
            SELECT method.id AS id, title, name, stage, classification, isLittle, 
                    isPlain, isTrebleDodging, isDifferential, notation, lengthOfLead, 
                    huntbellPath, numberOfHunts, leadHeadCode, 
                    leadHead, symmetry, fchGroups AS fchGroup, notes, rwReference
            FROM method
            INNER JOIN property ON method.propertyId = property.id
            WHERE title LIKE '%' || :name || '%' 
                AND stage = CASE WHEN :stage = 0
                    THEN stage
                    ELSE :stage
                    END
            ORDER BY stage, title
            LIMIT :limit
        """)
    abstract fun methodsByName(
        name: String,
        stage: Int,
        limit: Int
    ): Flow<List<MethodPropertyEntity>>
}