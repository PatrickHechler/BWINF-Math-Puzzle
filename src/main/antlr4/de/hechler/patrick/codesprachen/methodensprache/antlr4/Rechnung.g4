grammar Rechnung;

@header {
import de.hechler.patrick.puzzles.mathpuzzles.enums.*;
import de.hechler.patrick.puzzles.mathpuzzles.objects.*;
}

rechnung returns [Rechnung gl]
:
	g = punktRechnung
	{$gl = $g.gl;}
	(
		o = strichOperator g = punktRechnung
		{$gl = $gl.append($o.op, $g.gl);}
	)* EOF
;

strichOperator returns [Operator op]
:
	PLUS
	{$op = Operator.plus;}
	|
	MINUS
	{$op = Operator.minus;}
;

punktRechnung returns [Rechnung gl]
:
	g = direkteRechnung
	{$gl = $g.gl;}
	(
		o = punktOperator g = direkteRechnung
		{$gl = $gl.append($o.op, $g.gl);}
	)*
;

punktOperator returns [Operator op]
:
	MAL
	{$op = Operator.mal;}
	|
	GETEILT
	{$op = Operator.geteilt;}
;

direkteRechnung returns [Rechnung gl]
:
	t = ZAHL
	{$gl = new ZahlRechnung(Long.parseLong($t.getText()));}
	|
	KLAMMER_AUF g = rechnung KLAMMER_ZU
	{$gl = $g.gl;}
;

KLAMMER_AUF
:
	'('
;

KLAMMER_ZU
:
	')'
;

ZAHL
:
	[0-9]+
;

PLUS
:
	'+'
;

MINUS
:
	'-'
;

MAL
:
	'*'
;

GETEILT
:
	':'
	| '/'
;

WS
:
	[ \t\r\n]+ -> skip
;
