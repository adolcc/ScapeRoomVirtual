import model.EscapeRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.dao.EscapeRoomDAOImpl;
import repository.dao.GenericDAO;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

class EscapeRoomDAOImplTest {
    private GenericDAO<EscapeRoom, Long> escapeRoomDAO;
    private EscapeRoom escapeRoom;
    private EscapeRoom savedEscapeRoom;

    @BeforeEach
    void setUp() {
        escapeRoomDAO = new EscapeRoomDAOImpl();
        escapeRoom = new EscapeRoom("Ciudad Futura");
        savedEscapeRoom = escapeRoomDAO.save(escapeRoom);
    }

    @Test
    void givenNewEscapeRoom_whenSave_thenShouldPersist() {

        assertThat(savedEscapeRoom.getId()).inNotNull();
        assertThat(savedEscapeRoom.getName()).isEqualTo("Ciudad Futura");
    }

    @Test
    void givenSavedEscapeRoom_whenFindById_thenReturnsCorrectEscapeRoom() {

        Long savedId = savedEscapeRoom.getId();

        Optional<EscapeRoom> found = escapeRoomDAO.findById(savedId);

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Ciudad Futura");
    }

    @Test
    void givenSavedEscapeRoom_whenFindByName_thenReturnsCorrectEscapeRoom() {
        Optional<EscapeRoom> found = escapeRoomDAO.findByName("Ciudad Futura");

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Ciudad Futura");
    }

    @Test
    void givenNonExistingId_whenFindById_thenShouldReturnEmpty() {
        Optional<EscapeRoom> found = escapeRoomDAO.findById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void givenNonExistingName_whenFindByName_thenShouldReturnEmpty() {
        Optional<EscapeRoom> found = escapeRoomDAO.findByName("No Existe");

        assertThat(found).isEmpty();
    }

    @Test
    void givenMultipleEscapeRooms_whenFindAll_thenShouldReturnAll() {
        escapeRoomDAO.save(new EscapeRoom("La Casa Encantada"));
        escapeRoomDAO.save(new EscapeRoom("El Templo de Orión"));

        List<EscapeRoom> allEscapeRooms = escapeRoomDAO.findAll();

        assertThat(allEscapeRooms).hasSize(3);

        List<String> names = allEscapeRooms.stream()
                .map(EscapeRoom::getName)
                .toList();
        assertThat(names).containsExactlyInAnyOrder("Ciudad Futura", "El Templo de Orión", "La Casa Encantada");
    }

    @Test
    void givenEscapeRoom_whenDelete_thenGetsRemovedFromDatabase() {

        escapeRoomDAO.delete(savedEscapeRoom.getId());

        assertThat(escapeRoomDAO.findById(savedEscapeRoom.getId())).isEmpty();
        assertThat(escapeRoomDAO.existsByName("Ciudad Futura")).isFalse();
    }

}
