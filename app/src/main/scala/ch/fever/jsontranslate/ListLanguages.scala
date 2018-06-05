package ch.fever.jsontranslate

object ListLanguages extends TranslatorContextComponent {

  def apply(): Unit = {
    println(context.translator.langSupport.map(l ⇒ s"${l._1}\t${l._2}").mkString("\n"))
  }
}
