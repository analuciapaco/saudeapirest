/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.service;

import java.rmi.server.UID;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.conexa.saudeapirest.dto.PacienteDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.models.Paciente;
import com.conexa.saudeapirest.repository.PacienteRepository;
import com.conexa.saudeapirest.util.Utilitario;

@Service
public class PacienteService {
	private PacienteRepository pacienteRepository;
	private ModelMapper modelMapper = new ModelMapper();
	private Utilitario utilitario = new Utilitario();

	public PacienteService(PacienteRepository pacienteRepository) {
		this.pacienteRepository = pacienteRepository;
	}

	public Paciente buscarPacientePorId(String id) {
		Optional<Paciente> paciente=pacienteRepository.findById(id);
		return paciente.get();
	}

	public List<Paciente> listarPacientes() {
		return pacienteRepository.findAll();
	}

	public Paciente salvarPaciente(Paciente paciente) throws MessageException {
		validarCamposPaciente(paciente);
		if(paciente.getId()==null)
		paciente.setId(utilitario.gerarId());
		
		return pacienteRepository.save(paciente);

	}

	public void deletarPaciente(Paciente paciente) {
		pacienteRepository.delete(paciente);

	}

	public List<PacienteDTO> converterPacienteListParaPacienteDTOList(List<Paciente> lista) {
		List<PacienteDTO> listaPacienteDTO = lista.stream()
				.map(paciente -> modelMapper.map(paciente, PacienteDTO.class)).collect(Collectors.toList());
		return listaPacienteDTO;
	}

	public PacienteDTO converterPacienteParaPacienteDTO(Paciente paciente) {
		this.modelMapper.typeMap(PacienteDTO.class, Paciente.class);
		return this.modelMapper.map(paciente, PacienteDTO.class);

	}

	public Paciente converterPacienteDTOParaPaciente(PacienteDTO pacienteDTO) {
		this.modelMapper.typeMap(Paciente.class, PacienteDTO.class);
		return this.modelMapper.map(pacienteDTO, Paciente.class);

	}

	public void validarCamposPaciente(Paciente paciente) {
		if (paciente.getNome().isEmpty())
			throw new MessageException("O nome do paciente não pode ficar em branco!", HttpStatus.CONFLICT);
		if (paciente.getCpf().isEmpty())
			throw new MessageException("O CPF do paciente não pode ficar em branco!", HttpStatus.CONFLICT);
		if (paciente.getIdade().equals(null) || paciente.getIdade().equals(""))
			throw new MessageException("A idade do paciente não pode ficar em branco!", HttpStatus.CONFLICT);

	}
	

}
