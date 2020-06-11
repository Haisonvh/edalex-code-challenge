
package challenge.database;

/**
 *
 * @author HaiSonVH
 */


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageModelRepository extends JpaRepository<MessageModel, Long>{
    
}
