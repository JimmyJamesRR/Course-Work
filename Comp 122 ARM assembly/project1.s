@@@ Project 1 Comp 282 Spring 2017
@@@ By Jamees Renger
@@@ Code referenced from the University of Victory Arm manual
@@@ Purpose of the file is to read from an input and then to output the
@@@ integers with an ongoing sum of the total ints, into the console

@ This means that the function equ-als the numeric code that follows it.  
@It basically allows short hand, is going to required for use with the recycled code.
	.equ SWI_Open, 0x66	@ Open a file to read from
	.equ SWI_PrChr,0x00   	@ Write an ASCII char to Stdout
	.equ SWI_PrStr, 0x69   	@ Write a null-ending string 
	.equ SWI_PrInt,0x6b   	@ Write an Integer
	.equ Stdout,  1        	@ Set output mode to be Output View
	.equ SWI_Close, 0x68	@ Close File
	.equ SWI_Exit, 0x11   	@ Stop execution
	.global _start
	.text
_start:

@ This is the data for inputing the input file, This is found in arm manual page 23
	ldr r0,=InFileName @ set Name for input file
	mov r1,#0 @ mode is input
	swi SWI_Open @ open file for input
	bcs InFileError @ if error?
	ldr r1,=InFileHandle @ load input file handle
	str r0,[r1] @ save the file handle

@ print an integer to Stdout
	mov R0,#Stdout       @ mode is Output view
	mov r1, #42          @ integer to print
	swi SWI_PrInt

@ print a new line as a string to Stdout
	mov R0,#Stdout       @ mode is Output view
	ldr r1, =EOL         @ end of line
	swi SWI_PrStr
	swi SWI_Exit  @ stop executing: end of program
	.data




@@@ This is the close file handling example, found on page 24.  Not Required, but this will prevent leaking resources at the program end.
	@ load the file handle
	ldr r0,=InFileHandle
	ldr r0,[r0]
	swi SWI_Close
	@ load the file handle
	ldr r0,=OutFileHandle
	ldr r0,[r0]
	swi SWI_Close
	.data
swi SWI_Exit  @ stop executing: end of program


@@@ File input handling

Message1: .asciz"Hello World!"
EOL:     .asciz   "\n"
NewL:    .ascii   "\n"
Blank:   .ascii   " "
ldrr0,PathName
movr1,#1 @ output mode
swiSWI_Open
...
PathName:
 .asciz "C:\Users\Corsair\Documents\input.txt"

@@@ Read int from file
ldr r0,=InputFileHandle
ldr r0,[r0]
swi 0x6c
bcs ReadError
@ the integer is now in r0

@@@ Storing the file path, attached to the file input handling 

InFileHandle: .word 0
InFileName: .asciz "input.txt"
@@@ OutFileName: .asciz "Outfile1.txt"
@@@ OutFileHandle: .word 0
	.end


@@@ Old Example Code, Delete prior to completion.	
@ print a string to Stdout
	mov R0,#Stdout       @ mode is Stdout
	ldr R1, =Message1    @ load address of Message1
	swi SWI_PrStr        @ display message to Stdout
@ print a new line as a string to Stdout
	mov R0,#Stdout       @ mode is Stdout
	ldr r1, =EOL         @ end of line
	swi SWI_PrStr
@ print a character to the screen
	mov R0, #'A          @ R0 = char to print 
	swi SWI_PrChr 
@ print a blank character (from data)
	ldr r0,=Blank
	ldrb r0,[r0]          @ R0 = char to print = blank
	swi SWI_PrChr
@ print a second character to Stdout
	mov R0, #'B          @ R0 = char to print 
	swi SWI_PrChr 
@ print a new line as a character to Stdout
	ldr r0,=NewL
	ldrb r0,[r0]          @ R0 = char to print = new line
	swi SWI_PrChr

@ print a new line as a string to Stdout
	mov R0,#Stdout       @ mode is Output view
	ldr r1, =EOL         @ end of line
	swi SWI_PrStr
	


