package it.jrolamo.security;

import com.querydsl.core.types.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import it.jrolamo.generics.mongodb.controller.PublicCrudController;
import it.jrolamo.generics.mongodb.domain.GroupCount;
import it.jrolamo.security.User;
import it.jrolamo.security.UserDTO;
import it.jrolamo.security.repository.UserRepository;

/**
 * @author Automatic Code Generator
 */
@RestController
@CrossOrigin
@RequestMapping("/api/user")
@Api(value = "TicketManager", tags = "Protected Crud Controller: Operazioni di gestione Utenti")
public class UserController extends PublicCrudController<UserDTO> {

    @ApiOperation(value = "Ricerca User con paginazione e filtri opzionali", notes = "Restituisce i dati degli user in formato JSON", response = UserDTO.class, produces = "application/json")
    @Override
    public Page<UserDTO> getAll(
            @QuerydslPredicate(root = User.class, bindings = UserRepository.class) Predicate predicate,
            @RequestParam(defaultValue = "20") Integer pageSize, @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String sortField) {
        return service.getAll(predicate, PageRequest.of(pageNumber, pageSize, direction, sortField));
    }

    @Override
    public Page<GroupCount> getAllGroupBy(
            @QuerydslPredicate(root = User.class, bindings = UserRepository.class) Predicate predicate,
            @RequestParam(defaultValue = "20") Integer pageSize, @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction,
            @RequestParam(defaultValue = "id") String sortField, @RequestParam String groupField) {
        return null;
    }

    @Override
    public Long count(@QuerydslPredicate(root = User.class, bindings = UserRepository.class) Predicate predicate) {
        return service.count(predicate);
    }

}
