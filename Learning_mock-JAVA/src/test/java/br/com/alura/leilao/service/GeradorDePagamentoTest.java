package br.com.alura.leilao.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Pagamento;
import br.com.alura.leilao.model.Usuario;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GeradorDePagamentoTest {

  private GeradorDePagamento gerador;

  @Mock
  private PagamentoDao pagamentoDao;

  @Captor
  private ArgumentCaptor<Pagamento> captorPagamento;

  @Mock
  private Clock clock;

  @BeforeEach
  public void beforeEach() {
    MockitoAnnotations.initMocks(this);
    this.gerador = new GeradorDePagamento(pagamentoDao, clock);
  }

  private Leilao leilao() {
    Leilao leilao = new Leilao("Celular",
        new BigDecimal("500"),
        new Usuario("Fulano"));

    Lance segundo = new Lance(new Usuario("Ciclano"),
        new BigDecimal("900"));

    leilao.propoe(segundo);

    return leilao;
  }

  @Test
  void deveriaCriarPagamentoParaVencedorDoLeilao() {
    Leilao leilao = leilao();
    Lance vencedor = leilao.getLances().get(0);

    LocalDate data = LocalDate.now();
    Instant instant = data.atStartOfDay(ZoneId.systemDefault()).toInstant();

    Mockito.when(clock.instant()).thenReturn(instant);
    Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

    gerador.gerarPagamento(vencedor);

    Mockito.verify(pagamentoDao).salvar(captorPagamento.capture());

    Pagamento pagamento = captorPagamento.getValue();

    Assert.assertEquals(vencedor.getValor(), pagamento.getValor());
    Assert.assertFalse(pagamento.getPago());
    Assert.assertEquals(vencedor.getUsuario(), pagamento.getUsuario());
    Assert.assertEquals(leilao, pagamento.getLeilao());
  }

  @Test
  void vencimentoDoPagamentoParaVencedorDoLeilaoQuandoEncerradoSexta() {
    Leilao leilao = leilao();
    Lance vencedor = leilao.getLances().get(0);

    LocalDate data = LocalDate.of(2022, 5, 27);
    Instant instant = data.atStartOfDay(ZoneId.systemDefault()).toInstant();

    Mockito.when(clock.instant()).thenReturn(instant);
    Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

    gerador.gerarPagamento(vencedor);

    Mockito.verify(pagamentoDao).salvar(captorPagamento.capture());

    Pagamento pagamento = captorPagamento.getValue();

    Assert.assertEquals(
        pagamento.getVencimento(),
        LocalDate.of(2022, 5, 30));
  }

  @Test
  void vencimentoDoPagamentoParaVencedorDoLeilaoQuandoEncerradoSabado() {
    Leilao leilao = leilao();
    Lance vencedor = leilao.getLances().get(0);

    LocalDate data = LocalDate.of(2022, 5, 28);
    Instant instant = data.atStartOfDay(ZoneId.systemDefault()).toInstant();

    Mockito.when(clock.instant()).thenReturn(instant);
    Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

    gerador.gerarPagamento(vencedor);

    Mockito.verify(pagamentoDao).salvar(captorPagamento.capture());

    Pagamento pagamento = captorPagamento.getValue();

    Assert.assertEquals(
        pagamento.getVencimento(),
        LocalDate.of(2022, 5, 30));
  }

  @Test
  void vencimentoDoPagamentoParaVencedorDoLeilaoQuandoEncerradoDomingo() {
    Leilao leilao = leilao();
    Lance vencedor = leilao.getLances().get(0);

    LocalDate data = LocalDate.of(2022, 5, 29);
    Instant instant = data.atStartOfDay(ZoneId.systemDefault()).toInstant();

    Mockito.when(clock.instant()).thenReturn(instant);
    Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

    gerador.gerarPagamento(vencedor);

    Mockito.verify(pagamentoDao).salvar(captorPagamento.capture());

    Pagamento pagamento = captorPagamento.getValue();

    Assert.assertEquals(
        pagamento.getVencimento(),
        LocalDate.of(2022, 5, 30));
  }

  @Test
  void vencimentoDoPagamentoParaVencedorDoLeilaoQuandoEncerradoSegunda() {
    Leilao leilao = leilao();
    Lance vencedor = leilao.getLances().get(0);

    LocalDate data = LocalDate.of(2022, 5, 30);
    Instant instant = data.atStartOfDay(ZoneId.systemDefault()).toInstant();

    Mockito.when(clock.instant()).thenReturn(instant);
    Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

    gerador.gerarPagamento(vencedor);

    Mockito.verify(pagamentoDao).salvar(captorPagamento.capture());

    Pagamento pagamento = captorPagamento.getValue();

    Assert.assertEquals(
        pagamento.getVencimento(),
        LocalDate.of(2022, 5, 31));
  }
}
