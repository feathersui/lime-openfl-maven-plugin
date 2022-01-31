package com.example;

import feathers.controls.Application;
import feathers.controls.Label;

class MyFeathersUIProject extends Application {
	public function new() {
		super();

		var label = new Label();
		label.text = "Hello, World!";
		addChild(label);
	}
}
