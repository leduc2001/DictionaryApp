package com.gtranslate;

import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;

public class Main {
	public static void main(String[] args) throws JavaLayerException, IOException {
		Translator translate = Translator.getInstance();
		String text = translate.translate("I am programmer", Language.ENGLISH, Language.PORTUGUESE);
		System.out.println(text);
	}
}
