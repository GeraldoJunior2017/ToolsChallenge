package com.creditCardPaymentService.resource;

import com.creditCardPaymentService.model.PagamentoRequest;
import com.creditCardPaymentService.model.Transacao;
import com.creditCardPaymentService.model.TransacaoResponse;
import com.creditCardPaymentService.service.TransacaoService;
import com.creditCardPaymentService.util.CodigoAutorizacaoGenerator;
import com.creditCardPaymentService.util.NSUGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estornos")
public class EstornoResource {

    private final TransacaoService transacaoService;

    @Autowired
    public EstornoResource(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
   public ResponseEntity<TransacaoResponse> processarEstorno(@RequestBody PagamentoRequest transacaoRequest) {
        try {
            Transacao transacao = transacaoRequest.getTransacao();

            Transacao estornoProcessado = transacaoService.processarEstorno(transacao);

            Transacao transacaoSalva = transacaoService.salvarTransacao(estornoProcessado);

            if (transacaoSalva.getDescricao().getNsu() == null) {
                String nsu = NSUGenerator.gerarNsu();
                transacaoSalva.getDescricao().setNsu(nsu);
            }
            if (transacaoSalva.getDescricao().getCodigoAutorizacao() == null) {
                String codigoAutorizacao = CodigoAutorizacaoGenerator.gerarCodigoAutorizacao();
                transacaoSalva.getDescricao().setCodigoAutorizacao(codigoAutorizacao);
            }

            TransacaoResponse transacaoResponse = new TransacaoResponse();
            transacaoResponse.setTransacao(transacaoSalva);

            return ResponseEntity.status(HttpStatus.CREATED).body(transacaoResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}