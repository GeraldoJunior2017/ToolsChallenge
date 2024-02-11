package com.creditCardPaymentService.resource;

import com.creditCardPaymentService.model.TransacaoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.creditCardPaymentService.model.Transacao;
import com.creditCardPaymentService.service.TransacaoService;
import com.creditCardPaymentService.util.CodigoAutorizacaoGenerator;
import com.creditCardPaymentService.util.NSUGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consultas")
public class ConsultaResource {

    private final TransacaoService transacaoService;

    @Autowired
    public ConsultaResource(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Transacao>>> listarTodasAsTransacoes() {
        List<Transacao> transacoes = transacaoService.listarTodos();

        if (!transacoes.isEmpty()) {
            for (Transacao transacao : transacoes) {
                if (transacao.getDescricao() != null && (transacao.getDescricao().getNsu() == null || transacao.getDescricao().getCodigoAutorizacao() == null)) {
                    if (transacao.getDescricao().getStatus().equals("NEGADO")) {
                        String nsu = NSUGenerator.gerarNsu();
                        String codigoAutorizacao = CodigoAutorizacaoGenerator.gerarCodigoAutorizacao();
                        transacao.getDescricao().setNsu(nsu);
                        transacao.getDescricao().setCodigoAutorizacao(codigoAutorizacao);
                        transacaoService.salvarTransacao(transacao);
                    }
                }
            }



        List<Map<String, Transacao>> transacoesFormatadas = new ArrayList<>();
            for (Transacao transacao : transacoes) {
                Map<String, Transacao> transacaoMap = new HashMap<>();
                transacaoMap.put("transacao", transacao);
                transacoesFormatadas.add(transacaoMap);
            }

            return ResponseEntity.ok(transacoesFormatadas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/pagamentos/{id}")
    public ResponseEntity<Map<String, Transacao>> consultarPagamentoPorId(@PathVariable Long id) {
        Transacao pagamento = transacaoService.consultarPagamentoPorId(id);

        if (pagamento != null && !"NEGADO".equals(pagamento.getDescricao().getStatus())) {
            Map<String, Transacao> response = new HashMap<>();
            response.put("transacao", pagamento);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estornos/{id}")
    public ResponseEntity<TransacaoResponse> consultarEstornoPorId(@PathVariable Long id) {
        Transacao estorno = transacaoService.consultarEstornoPorId(id);

        if (estorno != null) {
            if (estorno.getDescricao().getNsu() == null) {
                estorno.getDescricao().setNsu(NSUGenerator.gerarNsu());
            }
            if (estorno.getDescricao().getCodigoAutorizacao() == null) {
                estorno.getDescricao().setCodigoAutorizacao(CodigoAutorizacaoGenerator.gerarCodigoAutorizacao());
            }

            TransacaoResponse transacaoResponse = new TransacaoResponse();
            transacaoResponse.setTransacao(estorno);

            return ResponseEntity.ok(transacaoResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
