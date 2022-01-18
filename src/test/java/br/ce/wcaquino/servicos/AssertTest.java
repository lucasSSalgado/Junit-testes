package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {

	@Test
	public void Test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals(0.555, 0.555, 0.01);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int x = 5;
		Integer y = 5;
		
		//Assert.assertEquals(x, y); incapaz de comparar int com Integer na classe Assert. Um dos 2 deve ser transformado
		Assert.assertEquals(Integer.valueOf(x), y);
		Assert.assertEquals(x, y.intValue());
		
		Assert.assertEquals("Bola", "Bola"); //deve ser exatamente igual
		Assert.assertTrue("Bola".equalsIgnoreCase("bola")); 
		Assert.assertTrue("bola".startsWith("bo"));
		
		Usuario u1 = new Usuario("Maria");
		Usuario u2 = new Usuario("Maria");
		Usuario u3 = null;
		
		Assert.assertEquals(u1, u2);
		
		Assert.assertSame(u1, u1); //compara se os obj estão na mesma "caixinha" de memoria
		
		Assert.assertNull(u3);
	}
}
