package jcolonia.daw.redipv4;

public class Ensayos {
	public static void main(String[] args) {
		long total;
		byte uno = 1;

		String txtBinario;

		for (int i = 0; i < 64; i++) {
			total = (long) uno << i;
			txtBinario = Long.toBinaryString(total);
			txtBinario = String.format("%64.64s", txtBinario);
			txtBinario = txtBinario.replace(' ', '0');
			System.out.printf("1<<%02d → %20s (%64.64sb)%n", i, Long.toUnsignedString(total), txtBinario);
		}

	}

}
