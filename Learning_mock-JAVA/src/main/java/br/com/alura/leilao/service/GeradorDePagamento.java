package br.com.alura.leilao.service;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.leilao.dao.PagamentoDao;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Pagamento;

@Service
public class GeradorDePagamento {

	private PagamentoDao pagamentos;
	private Clock clock;

	@Autowired
	public GeradorDePagamento(PagamentoDao pagamentos, Clock clock) {
		this.pagamentos = pagamentos;
		this.clock = clock;
	}

	private LocalDate proximoDiaUtil(LocalDate dataBase) {
		DayOfWeek diaDaSemana = dataBase.getDayOfWeek();

		LocalDate dateToReturn;

		if (diaDaSemana == DayOfWeek.SATURDAY)
			dateToReturn = dataBase.plusDays(2);
		else if (diaDaSemana == DayOfWeek.SUNDAY)
			dateToReturn = dataBase.plusDays(1);
		else
			dateToReturn = dataBase;

		return dateToReturn;
	}

	public void gerarPagamento(Lance lanceVencedor) {
		LocalDate vencimentoReferencia = LocalDate.now(clock).plusDays(1);
		Pagamento pagamento = new Pagamento(lanceVencedor, proximoDiaUtil(vencimentoReferencia));
		this.pagamentos.salvar(pagamento);
	}

}
