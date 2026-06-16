package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.mapper.UserMapper;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.UserRepo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;







@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;


    public UserService(UserRepo userRepo, UserMapper userMapper){
        this.userRepo = userRepo;
        this.userMapper= userMapper;
    }

    //Cadastrar usuário
    public void criarUsuario(UserRegisterRequestDTO request){
        //Checando se tem usuario com o email informado
        if (userRepo.findByEmail(request.email()).isPresent()){
            //Se possui o flow de registro para
            throw new RuntimeException("Ja existe Usuario cadastrado com o email "+ request.email());
        }

        //Se náo tiver usuario ja criado entao fazemos o registro
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhone(request.phone());
        user.setRole(Role.CUSTOMER);

        userRepo.save(user);

    }


    //Buscar usuário por ID
    public UserDTO buscarUsuarioPorId(Long id){
        //Procurando usuario
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("Não foi possivel encontrar um usuario com id "+id));
        //Usando a classe UserMapper para converter a entidade em DTO
        UserDTO dto = userMapper.userParaDTO(user);
        return dto;
    }

    //Buscar todos os usuários
    public List<UserDTO> buscarTodosUsuarios(){
        List<User> users = userRepo.findAll();
        List<UserDTO> dtos = userMapper.usersParaDTO(users);
        return dtos;
    }

    //login user

    //Atualizar usuário
    public void atualizarUsuarioPorId(Long userId, User update){
        if (update == null){
            return;
        }

        //Encontrando user
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException());
        //Checando se ah dados a serem atualizados, se sim entáo fazemos as alteracoes
        user.setName(update.getName() != null ? update.getName() : user.getName());
        user.setEmail(update.getEmail() != null ? update.getEmail() : user.getEmail());
        user.setPassword(update.getPassword() != null ? update.getPassword() : user.getPassword());
        user.setPhone(update.getPhone() != null ? update.getPhone() : user.getPhone());

        userRepo.save(user);
    }

    //Deletar usuário
    public void deletarUsuarioPorId(Long userId){
        userRepo.deleteById(userId);
    }
}
