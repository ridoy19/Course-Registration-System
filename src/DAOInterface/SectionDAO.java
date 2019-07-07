package DAOInterface;

import model.Section;

import java.util.List;
import java.util.function.Predicate;

public interface SectionDAO {
    Section openSection(Section section);
    Section retrieveSection(int id);
    List<Section> retrieveAll();
    Section modifySection(int id, int seatLimit, String room, String faculty);
    List<Section> findSection(Predicate<Section> predicate);
    boolean dropSection(int id);
    boolean dropAllSection();


}
