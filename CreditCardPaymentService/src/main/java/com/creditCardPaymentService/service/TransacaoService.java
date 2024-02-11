package com.creditCardPaymentService.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.creditCardPaymentService.model.Transacao;
import com.creditCardPaymentService.repository.PagamentoRepository;
import com.creditCardPaymentService.util.CodigoAutorizacaoGenerator;
import com.creditCardPaymentService.util.NSUGenerator;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

	private final PagamentoRepository pagamentoRepository;

	@Autowired
	public TransacaoService(PagamentoRepository pagamentoRepository) {
		this.pagamentoRepository = pagamentoRepository;
	}

	public List<Transacao> listarTransacoes() {
		return pagamentoRepository.findAll();
	}

	public Transacao salvarTransacao(Transacao transacao) {
	    try {
	        String nsu = NSUGenerator.gerarNsu();
	        String codigoAutorizacao = CodigoAutorizacaoGenerator.gerarCodigoAutorizacao();

	        transacao.getDescricao().setNsu(nsu);
	        transacao.getDescricao().setCodigoAutorizacao(codigoAutorizacao);

	        Transacao transacaoSalva = pagamentoRepository.save(transacao);

	        return transacaoSalva;
	    } catch (Exception e) {
	        return null;
	    }
	}


	public Transacao processarEstorno(Transacao estorno) {
		Optional<Transacao> optionalTransacao = pagamentoRepository.findById(estorno.getId());
		Transacao transacaoExistente;

		if (optionalTransacao.isPresent()) {
			transacaoExistente = optionalTransacao.get();
			if ("NEGADO".equals(transacaoExistente.getDescricao().getStatus())) {
				throw new RuntimeException("A transação já foi negada.");
			}
			transacaoExistente.getDescricao().setStatus("NEGADO");
		} else {
			transacaoExistente = estorno;
			transacaoExistente.getDescricao().setStatus("NEGADO");
			transacaoExistente.setId(estorno.getId());
		}

		return pagamentoRepository.save(transacaoExistente);
	}

	public Transacao consultarEstornoPorId(Long id) {
		Optional<Transacao> optionalTransacao = pagamentoRepository.findById(id);
		if (optionalTransacao.isPresent()) {
			Transacao transacao = optionalTransacao.get();
			if ("NEGADO".equals(transacao.getDescricao().getStatus())) {
				return transacao;
			}
		}
		return null;
	}

	public List<Transacao> listarTodos() {
	    return pagamentoRepository.findAll();
	}


	public Transacao consultarPagamentoPorId(Long id) {
		Optional<Transacao> optionalTransacao = pagamentoRepository.findById(id);
		return optionalTransacao.orElse(null);
	}



	
}
