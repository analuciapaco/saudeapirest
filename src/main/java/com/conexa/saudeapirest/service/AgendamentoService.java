/**
 * @Empresa: Conexa Saúde
 * @author: Ana Lúcia Seles
  *@Versão da Classe = 1
 */

package com.conexa.saudeapirest.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.conexa.saudeapirest.dto.AgendamentoDTO;
import com.conexa.saudeapirest.dto.AgendamentoDoDiaDTO;
import com.conexa.saudeapirest.dto.UsuarioDTO;
import com.conexa.saudeapirest.dto.UsuarioMedicoDTO;
import com.conexa.saudeapirest.exception.MessageException;
import com.conexa.saudeapirest.models.Agendamento;
import com.conexa.saudeapirest.models.Usuario;
import com.conexa.saudeapirest.repository.AgendamentoRepository;
import com.conexa.saudeapirest.util.Utilitario;

@Service
public class AgendamentoService {
	private final AgendamentoRepository agendamentoRepository;
	private UsuarioService usuarioService;
	private MedicoService medicoService;
	private PacienteService pacienteService;
	private ModelMapper modelMapper = new ModelMapper();
	private Utilitario utilitario = new Utilitario();

	public AgendamentoService(AgendamentoRepository agendamentoRepository, UsuarioService usuarioService,
			MedicoService medicoService, PacienteService pacienteService) {
		this.agendamentoRepository = agendamentoRepository;
		this.usuarioService = usuarioService;
		this.medicoService = medicoService;
		this.pacienteService = pacienteService;
	}

	public UsuarioMedicoDTO carregarUsuarioMedicoDTO(UsuarioDTO usuarioDTO, String authorization) {
		LocalDate dataAtual = LocalDate.now();
		List<Agendamento> listaAgendamentosDoDia = agendamentoRepository
				.findByAgendamentosDoDia(utilitario.converte(dataAtual));
		List<Agendamento> listaAgendamentoDoMedico = listaAgendamentosDoDia.stream()
				.filter(agendamento -> agendamento.getMedico().getId() == (usuarioDTO.getMedico().getId()))
				.collect(Collectors.toList());
		UsuarioMedicoDTO usuarioMedicoDTO = montarUsuarioMedicoDTO(usuarioDTO, authorization);
		usuarioMedicoDTO.setAgendamentos_hoje(carregarAgendamentosDoDia(listaAgendamentoDoMedico));
		return usuarioMedicoDTO;
	}

	public List<AgendamentoDoDiaDTO> carregarAgendamentosDoDia(List<Agendamento> listaAgendamento) {
		AgendamentoDoDiaDTO agendamentoDoDiaDTO = new AgendamentoDoDiaDTO();
		List<AgendamentoDoDiaDTO> listaAgendamentoDoDia = new ArrayList<>();
		listaAgendamento.stream().forEach(agendamento -> {
			agendamentoDoDiaDTO.setData_hora_atendimento(agendamento.getData_hora_atendimento());
			agendamentoDoDiaDTO.setId_paciente(agendamento.getPaciente().getId());
			listaAgendamentoDoDia.add(agendamentoDoDiaDTO);
		});
		return listaAgendamentoDoDia;
	}

	public UsuarioMedicoDTO montarUsuarioMedicoDTO(UsuarioDTO usuarioDTO, String authorization) {
		UsuarioMedicoDTO usuarioMedicoDTO = new UsuarioMedicoDTO();
		usuarioMedicoDTO.setMedico(usuarioDTO.getMedico().getNome());
		usuarioMedicoDTO.setEspecialidade(usuarioDTO.getMedico().getEspecialidade());
		usuarioMedicoDTO.setToken(authorization);
		return usuarioMedicoDTO;
	}

	public AgendamentoDTO converterAgendamentoParaAgendamentoDTO(Agendamento agendamento) {
		this.modelMapper.typeMap(AgendamentoDTO.class, Agendamento.class).addMapping(AgendamentoDTO::getMedico,
				Agendamento::setMedico);
		return this.modelMapper.map(agendamento, AgendamentoDTO.class);

	}

	public Agendamento converterAgendamentoDTOParaAgendamento(AgendamentoDTO agendamentoDTO) {
		this.modelMapper.typeMap(Agendamento.class, AgendamentoDTO.class).addMapping(Agendamento::getMedico,
				AgendamentoDTO::setMedico);
		return this.modelMapper.map(agendamentoDTO, Agendamento.class);

	}

	public AgendamentoDTO salvarAgendamentoDoDia(AgendamentoDoDiaDTO agendamentoDoDiaDTO, String authorization)
			throws MessageException {
		Usuario usuario = usuarioService.buscarUsuarioPeloToken(authorization);
		Agendamento agendamento = new Agendamento();
		agendamento.setData_hora_atendimento(agendamentoDoDiaDTO.getData_hora_atendimento());
		agendamento.setPaciente(pacienteService.buscarPacientePorId(agendamentoDoDiaDTO.getId_paciente()));
		agendamento.setMedico(medicoService.buscarMedicoPorId(usuario.getMedico().getId()));
		validarCamposAgendamento(agendamento);
		return converterAgendamentoParaAgendamentoDTO(salvarAgendamento(agendamento));
	}

	public Agendamento salvarAgendamento(Agendamento agendamento) throws MessageException {
		validarCamposAgendamento(agendamento);
		return agendamentoRepository.save(agendamento);
	}

	public List<Agendamento> listarAgendamentos() {
		return agendamentoRepository.findAll();
	}

	public Agendamento buscarAgendamentoPorId(long id) {
		return agendamentoRepository.findById(id);
	}

	public void deletarAgendamento(Agendamento agendamento) {
		agendamentoRepository.delete(agendamento);
	}

	public List<AgendamentoDTO> converterAgendamentoListParaAgendamentoDTOList(List<Agendamento> lista) {
		List<AgendamentoDTO> listaAgendamentoDTO = lista.stream()
				.map(agendamento -> modelMapper.map(agendamento, AgendamentoDTO.class)).collect(Collectors.toList());
		return listaAgendamentoDTO;
	}

	public void validarCamposAgendamento(Agendamento agendamento) {
		if (agendamento.getData_hora_atendimento().equals(""))
			throw new MessageException("O nome do médico não pode ficar em branco!", HttpStatus.CONFLICT);
		if (agendamento.getPaciente() == null)
			throw new MessageException("O nome do paciente não pode ficar em branco!", HttpStatus.CONFLICT);
		if (agendamento.getMedico() == null)
			throw new MessageException("O médico não pode ficar em branco!", HttpStatus.CONFLICT);

	}

}
