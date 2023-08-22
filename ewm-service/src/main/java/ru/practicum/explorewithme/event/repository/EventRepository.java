package ru.practicum.explorewithme.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.Status;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiatorId(long id, PageRequest pageRequest);

    List<Event> findAllByLocationNameIgnoreCase(String name);

    List<Event> findAllByLocationAddressIgnoreCase(String address);

    List<Event> findAllByLocationLatAndLocationLon(Double lat, Double lon);

    List<Event> findAllByLocationAddressAndLocationName(String address, String name);

    List<Event> findAllByLocationNameIgnoreCaseAndLocationAddressIgnoreCaseAndLocationLatAndLocationLon(String name,
                                                                                                        String address,
                                                                                                        Double lat,
                                                                                                        Double lon);

    List<Event> findAllByInitiatorInAndStateInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(List<User> users,
                                                                                                   List<Status> states,
                                                                                                   List<Category> categories,
                                                                                                   LocalDateTime start,
                                                                                                   LocalDateTime end,
                                                                                                   PageRequest pageRequest);

    List<Event> findAllByInitiatorInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(List<User> users,
                                                                                         List<Category> categories,
                                                                                         LocalDateTime start,
                                                                                         LocalDateTime end,
                                                                                         PageRequest pageRequest);

    List<Event> findAllByStateInAndCategoryInAndEventDateIsAfterAndEventDateIsBefore(List<Status> states,
                                                                                     List<Category> categories,
                                                                                     LocalDateTime start,
                                                                                     LocalDateTime end,
                                                                                     PageRequest pageRequest);

    List<Event> findAllByInitiatorInAndStateInAndEventDateIsAfterAndEventDateIsBefore(List<User> users,
                                                                                      List<Status> states,
                                                                                      LocalDateTime start,
                                                                                      LocalDateTime end,
                                                                                      PageRequest pageRequest);

    List<Event> findAllByCategoryInAndEventDateIsAfterAndEventDateIsBefore(List<Category> categories,
                                                                           LocalDateTime start,
                                                                           LocalDateTime end,
                                                                           PageRequest pageRequest);

    List<Event> findAllByInitiatorInAndEventDateIsAfterAndEventDateIsBefore(List<User> users,
                                                                            LocalDateTime start,
                                                                            LocalDateTime end,
                                                                            PageRequest pageRequest);

    List<Event> findAllByStateInAndEventDateIsAfterAndEventDateIsBefore(List<Status> states,
                                                                        LocalDateTime start,
                                                                        LocalDateTime end,
                                                                        PageRequest pageRequest);

    List<Event> findAllByEventDateIsAfterAndEventDateIsBefore(LocalDateTime start, LocalDateTime end,
                                                              PageRequest pageRequest);


    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.paid = ?2 " +
            "AND e.eventDate > ?3 " +
            "AND e.eventDate < ?4 " +
            "AND e.category IN (?5) " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(String text, Boolean paid, LocalDateTime start, LocalDateTime end,
                                     List<Category> categories, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.category IN (?4) " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(String text, LocalDateTime start, LocalDateTime end, List<Category> categories,
                                     PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.paid = ?2 " +
            "AND e.eventDate > ?3 " +
            "AND e.eventDate < ?4 " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(String text, Boolean paid, LocalDateTime start, LocalDateTime end,
                                     PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.paid = ?1 " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.category IN (?4) " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(Boolean paid, LocalDateTime start, LocalDateTime end, List<Category> categories,
                                     PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(String text, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.eventDate > ?1 " +
            "AND e.eventDate < ?2 " +
            "AND e.category IN (?3) " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(LocalDateTime start, LocalDateTime end, List<Category> categories,
                                     PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.paid = ?1 " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(Boolean paid, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.eventDate > ?1 " +
            "AND e.eventDate < ?2 " +
            "AND e.state = 'PUBLISHED' " +
            "AND e.confirmedRequests < e.participantLimit")
    List<Event> findAllOnlyAvailable(LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.paid = ?2 " +
            "AND e.eventDate > ?3 " +
            "AND e.eventDate < ?4 " +
            "AND e.category IN (?5) " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(String text, Boolean paid, LocalDateTime start, LocalDateTime end, List<Category> categories,
                        PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.category IN (?4) " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(String text, LocalDateTime start, LocalDateTime end, List<Category> categories,
                        PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.paid = ?2 " +
            "AND e.eventDate > ?3 " +
            "AND e.eventDate < ?4 " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(String text, Boolean paid, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE (LOWER(e.annotation) LIKE %?1% " +
            "OR LOWER(e.description) LIKE %?1%) " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(String text, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.eventDate > ?1 " +
            "AND e.eventDate < ?2 " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.paid = ?1 " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.category IN (?4) " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(Boolean paid, LocalDateTime start, LocalDateTime end, List<Category> categories,
                        PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.eventDate > ?1 " +
            "AND e.eventDate < ?2 " +
            "AND e.category IN (?3) " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(LocalDateTime start, LocalDateTime end, List<Category> categories, PageRequest pageRequest);

    @Query("SELECT e FROM events AS e " +
            "WHERE e.paid = ?1 " +
            "AND e.eventDate > ?2 " +
            "AND e.eventDate < ?3 " +
            "AND e.state = 'PUBLISHED'")
    List<Event> findAll(Boolean paid, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);
}