package com.carros.domain;

import com.carros.domain.dto.CarroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarroService {
    @Autowired
    CarroRepository carroRepository;

    public List<CarroDTO> getCarros() {

        return carroRepository.findAll().stream().map(CarroDTO::create)
                .collect(Collectors.toList());
    }

    public Optional<CarroDTO> getCarroById(Long id) {

        return carroRepository.findById(id).map(CarroDTO::create);
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {

        return carroRepository.findByTipo(tipo).stream().map(CarroDTO::create)
                .collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");

        return CarroDTO.create(carroRepository.save(carro));
    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id,"Não foi possível atualizar o registro");

        return carroRepository.findById(id).map(db -> {
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            carroRepository.save(db);

            return CarroDTO.create(db);
        }).orElseThrow(() -> new RuntimeException("Não foi possível atualizar o registro"));
    }

    public void delete(Long id) {
        if(carroRepository.existsById(id)) {
            carroRepository.deleteById(id);
        } else {
            throw new RuntimeException("Não foi possível deletar o registro");
        }
    }

}
