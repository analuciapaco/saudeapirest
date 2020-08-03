/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.service;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.conexa.saudeapirest.dto.MedicoDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.models.Medico;
import com.conexa.saudeapirest.repository.MedicoRepository;

@Service
public class MedicoService {
	private final MedicoRepository medicoRepository;
	private ModelMapper modelMapper = new ModelMapper();

	public MedicoService(MedicoRepository medicoRepository) {
		this.medicoRepository = medicoRepository;
	}

	public Medico buscarMedicoPorId(long medico) {
		return medicoRepository.findById(medico);
	}

	public void deletarMedico(Medico medico) {

		medicoRepository.delete(medico);
	}

	public List<Medico> listarMedicos() {
		return medicoRepository.findAll();
	}

	public Medico salvarMedico(Medico medico) throws MessageException {
		validarCamposMedico(medico);
		return medicoRepository.save(medico);
	}

	public List<MedicoDTO> converterMedicoListParaMedicoDTOList(List<Medico> lista) {
		List<MedicoDTO> listaMedicoDTO = lista.stream().map(medico -> modelMapper.map(medico, MedicoDTO.class))
				.collect(Collectors.toList());
		return listaMedicoDTO;
	}

	public MedicoDTO converterMedicoParaMedicoDTO(Medico medico) {
		this.modelMapper.typeMap(MedicoDTO.class, Medico.class);
		return this.modelMapper.map(medico, MedicoDTO.class);

	}

	public Medico converterMedicoDTOParaMedico(MedicoDTO medicoDTO) {
		this.modelMapper.typeMap(Medico.class, MedicoDTO.class);
		return this.modelMapper.map(medicoDTO, Medico.class);

	}

	public void validarCamposMedico(Medico medico) {
		if (medico.getNome().isEmpty())
			throw new MessageException("O nome do médico não pode ficar em branco!", HttpStatus.CONFLICT);
		if (medico.getEspecialidade().isEmpty())
			throw new MessageException("A especialidade do médico não pode ficar em branco!", HttpStatus.CONFLICT);

	}

}