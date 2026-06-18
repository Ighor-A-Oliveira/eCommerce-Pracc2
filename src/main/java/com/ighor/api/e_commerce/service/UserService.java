package com.ighor.api.e_commerce.service;

import com.ighor.api.e_commerce.dto.entity.UserDTO;
import com.ighor.api.e_commerce.dto.request.UserLoginRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserRegisterRequestDTO;
import com.ighor.api.e_commerce.dto.request.UserUpdateRequestDTO;
import com.ighor.api.e_commerce.dto.response.UserLoginResponseDTO;
import com.ighor.api.e_commerce.exception.DuplicateResourceException;
import com.ighor.api.e_commerce.exception.ResourceNotFoundException;
import com.ighor.api.e_commerce.mapper.UserMapper;
import com.ighor.api.e_commerce.model.entity.User;
import com.ighor.api.e_commerce.model.enums.Role;
import com.ighor.api.e_commerce.repo.UserRepo;
import com.ighor.api.e_commerce.security.TokenConfig;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;







@Service
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenConfig tokenConfig){
        this.userRepo = userRepo;
        this.userMapper= userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    //Cadastrar usuário
    @Transactional
    public void criarUsuario(UserRegisterRequestDTO request){
        //Checando se tem usuario com o email informado
        if (userRepo.findByEmail(request.email()).isPresent()){
            //Se possui o flow de registro para
            throw new DuplicateResourceException("Ja existe Usuario cadastrado com o email "+ request.email());
        }

        //Se náo tiver usuario ja criado entao fazemos o registro
        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setRole(Role.CUSTOMER);

        userRepo.save(user);
    }


    //Buscar usuário por ID
    public UserDTO buscarUsuarioPorId(Long id){
        //Procurando usuario
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Não foi possivel encontrar um usuario com id "+id));
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
    @Transactional
    public UserLoginResponseDTO fazerLoginUsuario(UserLoginRequestDTO request) {
        //Cria o token de autenticação interno do Spring
        //Spring Security só aceita login no formato UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        //Autentica no Spring Security:
        //Busca o usuário no banco (UserDetailsService)
        //Compara a senha com BCrypt (PasswordEncoder)
        //se estiver certo: retorna um objeto Authentication
        //se estiver errado: lança BadCredentialsException
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        //Recupera o usuário autenticado do banco de dados
        User user = (User) authentication.getPrincipal();
        //Gera o JWT para o usuário logado
        String token = tokenConfig.generateToken(user);



        //Retorna o token no body
        return new UserLoginResponseDTO(token);
    }

    //Atualizar usuário
    @Transactional
    public void atualizarUsuarioPorId(Long userId, UserUpdateRequestDTO update){
        if (update == null){
            return;
        }

        //Encontrando user
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Nao foi possivel encontrar um Usuario com o id "+userId));
        //Checando se ah dados a serem atualizados, se sim entáo fazemos as alteracoes
        user.setName(update.name() != null ? update.name() : user.getName());
        user.setEmail(update.email() != null ? update.email() : user.getEmail());
        user.setPhone(update.phone() != null ? update.phone() : user.getPhone());

        userRepo.save(user);
    }

    //Deletar usuário
    @Transactional
    public void deletarUsuarioPorId(Long userId){
        userRepo.deleteById(userId);
    }
}
