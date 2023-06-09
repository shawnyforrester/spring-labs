package com.revature.L4_art.Service;

import com.revature.L4_art.ArtApplication;
import com.revature.L4_art.Model.Artist;
import com.revature.L4_art.Model.Painting;
import com.revature.L4_art.Repository.ArtistRepository;
import com.revature.L4_art.Repository.PaintingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The @Service annotation may also be used instead of the @Component annotation, the @Service annotation is a
 * Stereotype annotation, which would be a component with a preset configuration that would be appropriate for a
 * Service class (in this case, @Service and @Component behave identically.)
 *
 * The @Transactional annotation wraps every method in this class inside a database transaction, which is a set of
 * database statements that happen in isolation of all other database transactions. This means that multiple methods
 * of the class can run simultaneously (Spring is multithreaded) without having an effect such as a dirty read,
 * reading data that has been modified in an in-progress transaction. This matters when a Transaction has multiple
 * steps. (for instance, when a single transaction should process 100 database statements for purchasing items from a
 * user's cart, we don't want Spring to make the mistake of reading from an incomplete transaction, as e.g. a
 * request for the total cost of a user's cart could return an erroneous amount, such as only 50 items, if Spring
 * happens to read an incomplete transaction.)
 */
@Service
@Transactional
public class PaintingService {
    PaintingRepository paintingRepository;
    ArtistRepository artistRepository;
    @Autowired
    public PaintingService(PaintingRepository paintingRepository, ArtistRepository artistRepository){
        ArtApplication.log.info("logging method execution: PaintingService constructor");
        this.paintingRepository = paintingRepository;
        this.artistRepository = artistRepository;
    }

    /**
     * Leverage the Spring Data JPARepository method save() to persist a new Painting to the Painting table. The
     * new painting is returned. This functionality is provided to allow you to test your API.
     * @param painting A transient Painting entity
     * @return the persisted Painting entity, if successful
     */
    public Painting savePainting(long artistID, Painting painting){
        Artist a = artistRepository.findById(artistID).get();
        a.getPaintings().add(painting);
        return paintingRepository.save(painting);
    }
    /**
     * TODO Problem 1: Leverage the Spring Data JPARepository method findAll() to retrieve all Paintings from the Painting table.
     * @return a list of all Painting entities
     */
    public List<Painting> getAllPaintings(){
        List<Painting> allPaintings = paintingRepository.findAll();
        return allPaintings;
    }
    /**
     * TODO Problem 2: Write a query method in Spring Data JPARepository to retrieve all Paintings by their genre.
     * @param genre
     * @return a list of all Painting entities with a particular genre.
     */
    public List<Painting> getAllPaintingsByGenre(String genre){
        return paintingRepository.findPaintingsByGenre(genre);
    }
    /**
     * TODO Problem 3: Write a query method in Spring Data JPARepository to retrieve all Paintings by their title.
     * @param title
     * @return a list of all Painting entities with a particular title.
     */
    public List<Painting> getAllPaintingsByTitle(String title){
        return paintingRepository.findPaintingByTitle(title);
    }
    /**
     * TODO Problem 4: Write a query method in Spring Data JPARepository to retrieve all Paintings by their title & genre.
     * @param title
     * @param genre
     * @return a list of all Painting entities with a particular title & genre.
     */
    public List<Painting> getAllPaintingsByTitleAndGenre(String title, String genre){
        return paintingRepository.findPaintingByGenreAndTitle(title, genre);
    }
}
