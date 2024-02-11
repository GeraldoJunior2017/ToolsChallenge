package com.creditCardPaymentService.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.creditCardPaymentService.model.PagamentoRequest;
import com.creditCardPaymentService.model.Transacao;
import com.creditCardPaymentService.service.TransacaoService;
import com.creditCardPaymentService.util.CodigoAutorizacaoGenerator;
import com.creditCardPaymentService.util.NSUGenerator;

@RestController
public class PagamentoResource {

    private final TransacaoService transacaoService;

    public PagamentoResource(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<PagamentoRequest> processarPagamento(@RequestBody PagamentoRequest transacaoRequest) {
        try {
            Transacao transacao = transacaoRequest.getTransacao();
            transacao.getDescricao().setStatus("AUTORIZADO");

            Transacao transacaoSalva = transacaoService.salvarTransacao(transacao);

            if (transacaoSalva != null) {
                if (transacaoSalva.getDescricao().getNsu() == null) {
                    String nsu = NSUGenerator.gerarNsu();
                    transacaoSalva.getDescricao().setNsu(nsu);
                }
                if (transacaoSalva.getDescricao().getCodigoAutorizacao() == null) {
                    String codigoAutorizacao = CodigoAutorizacaoGenerator.gerarCodigoAutorizacao();
                    transacaoSalva.getDescricao().setCodigoAutorizacao(codigoAutorizacao);
                }

                PagamentoRequest pagamentoResponse = new PagamentoRequest();
                pagamentoResponse.setTransacao(transacaoSalva);
                
                return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoResponse);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }    
}
