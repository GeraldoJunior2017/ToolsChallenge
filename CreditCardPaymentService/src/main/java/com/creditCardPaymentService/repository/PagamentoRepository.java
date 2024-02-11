package com.creditCardPaymentService.repository;



import org.springframework.data.jpa.repository.JpaRepository;


import com.creditCardPaymentService.model.Transacao;



public interface PagamentoRepository extends JpaRepository<Transacao, Long> {
}
