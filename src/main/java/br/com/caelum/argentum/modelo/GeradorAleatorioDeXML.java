package br.com.caelum.argentum.modelo;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class GeradorAleatorioDeXML {
	public static void main(String[] args) throws IOException {
		Calendar data = Calendar.getInstance();
		Random random = new Random(123);
		List<Negociacao> negocios = new ArrayList<Negociacao>();

		double valor = 40;
		int quantidade = 1000;

		for (int dias = 0; dias < 30; dias++) {
			int quantidadeNegociosDoDia = random.nextInt(20);

			for (int negocio = 0; negocio < quantidadeNegociosDoDia; negocio++) {
				// no m�ximo sobe ou cai R$1,00 e nao baixa al�m de R$5,00
				valor += (random.nextInt(200) - 100) / 100.0;
				if (valor < 5.0) {
					valor = 5.0;
				}

				// quantidade: entre 500 e 1500
				quantidade += 1000 - random.nextInt(500);

				Negociacao n = new Negociacao(valor, quantidade, data);
				negocios.add(n);
			}
			data = (Calendar) data.clone();
			data.add(Calendar.DAY_OF_YEAR, 1);
		}

		XStream stream = new XStream(new DomDriver());
		stream.alias("negocio", Negociacao.class);
		stream.setMode(XStream.NO_REFERENCES);

		PrintStream out = new PrintStream(new File("negocios.xml"));
		out.println(stream.toXML(negocios));
		
		out.close();
	}
}