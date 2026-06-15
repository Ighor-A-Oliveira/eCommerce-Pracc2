package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.mapper.UserMapper;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.UserRepo;
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

    //Deletar usuário
}
