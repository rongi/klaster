package com.github.rongi.stekker.samples.main.data

import com.github.rongi.stekker.samples.main.model.Article

object ArticlesProvider {

  fun getArticles() = articles

}

private val articles = listOf(
  Article("TextQL: Execute SQL Against CSV or TSV", id = 1),
  Article("Hash-based Signatures: An illustrated Primer", id = 2),
  Article("Next generation video: Introducing AV1", id = 3),
  Article("The Mathematics of 2048: Optimal Play with Markov Decision Processes", id = 4),
  Article("Uber enters dockless bike wars with Jump acquisition", id = 5),
  Article("Android container in Chrome OS ", id = 6),
  Article("Rational: Or why am I bothering to rewrite nanomsg?", id = 7),
  Article("Elm at Pacific Health Dynamics", id = 8),
  Article("ACE Submarine Cable Cut Impacts Ten Countries", id = 9),
  Article("Weirdstuff Warehouse is closed", id = 10),
  Article("A Python Interpreter Written in Python (2016)", id = 11),
  Article("OpenAI Charter", id = 12),
  Article("Probes Point to Northrop Grumman Errors in January SpaceX Spy-Satellite Failure", id = 13),
  Article("DE-Cix – Power failure leads to Downtime in germany", id = 14),
  Article("Quartzy (YC S11) Is Hiring Software Engineers (Palo Alto / Remote-US)", id = 15),
  Article("A Quantum Computer Simulator in 150 Lines of Code", id = 16),
  Article("Reverse Engineering WhatsApp Web", id = 17),
  Article("Project from Hell (2008)", id = 18),
  Article("Show HN: Wey – A fast, open-source Slack desktop app", id = 19),
  Article("Color: From Hex codes to Eyeballs", id = 20)
)