package com.example;

import openfl.display.Sprite;
import openfl.text.TextField;

class MyOpenFLProject extends Sprite {
	public function new() {
		super();

		var tf = new TextField();
		tf.text = "Hello, World!";
		addChild(tf);
	}
}
