@@@ Project 2 Comp 282
@@@ By: James Renger
@@@ Referenced Code From Arm Simulator Manual, Example 11.2
@@@ OPEN INPUT FILE, READ INTEGER FROM FILE, PRINT IT, ADD IT TO SUM
@@@ CLOSE INPUT FILE

@@@ Equ. for equ-als, removes use of OP code, for ease of use.
	.equ SWI_Open, 0x66        @open a file
	.equ SWI_Close,0x68        @close a file
	.equ SWI_PrChr,0x00        @ Write an ASCII char to Stdout
	.equ SWI_PrStr, 0x69       @ Write a null-ending string 
	.equ SWI_PrInt,0x6b        @ Write an Integer
	.equ SWI_RdInt,0x6c        @ Read an Integer from a file
	.equ Stdout,  1            @ Set output target to be Stdout
	.equ SWI_Exit, 0x11        @ Stop execution
.global _start
.text
_start:

@ print an initial message to the screen
	mov R0,#Stdout       	  @print an initial message 
	ldr R1, =Message1    	  @ load address of Message1 label
	swi SWI_PrStr        	  @ display message to Stdout
	ldr r1, =Space
	swi SWI_PrStr		  @ Space for... spacing...

@ == Open an input file for reading =============================

@ if problems, print message to Stdout and exit
	ldr r0,=InFileName        @ set Name for input file
	mov r1,#0                 @ mode is input
	swi SWI_Open              @ open file for input
	bcs InFileError      	  @ Check Carry-Bit (C): if= 1 then ERROR

@ Save the file handle in memory:
	ldr r1,=InFileHandle      @ if OK, load input file handle
	str r0,[r1]               @ save the file handle

@ == Read integers until end of file =============================
RLoop:
	ldr r0,=InFileHandle   	  @ load input file handle
	ldr r0,[r0]
	swi SWI_RdInt             @ read the integer into R0
	add r9,r9,r0		  @ Add new int to existing sum
	bcs EofReached       	  @ Check Carry-Bit (C): if= 1 then EOF reached

@ print the integer to Stdout
	mov r1,r0                 @ R1 = integer to print	
	mov R0,#Stdout            @ target is Stdout
	swi SWI_PrInt
	
	ldr R1, =ColonSpace    	  @ load address of spacing for output
	swi SWI_PrStr        	  @ display message to Stdout
			
	mov r1,r9                 @ R1 = Sum to print	
	mov R0,#Stdout            @ target is Stdout
	swi SWI_PrInt

	mov R0,#Stdout            @ print new line
	ldr r1, =NL
	swi SWI_PrStr

@ Before the end of the loop you need to restore the int vareiable into another registry and then perform the sum operation and restore the int into another element of thwe registry.

	bal RLoop                 @ keep reading till end of file

@ == End of file ===============================================

EofReached:
	mov R0, #Stdout           @ print last message
	ldr R1, =EndOfFileMsg
	swi SWI_PrStr
@ == Close a file ===============================================
	ldr R0, =InFileHandle     @ get address of file handle
	ldr R0, [R0]              @ get value at address
	swi SWI_Close
Exit:
swi SWI_Exit               	  @ stop executing 
InFileError:
mov R0, #Stdout
ldr R1, =FileOpenInpErrMsg
swi SWI_PrStr 
bal Exit                  	  @ give up, go to end
.data
.align
InFileHandle:    .skip    4
InFileName:      .asciz   "input.txt"
FileOpenInpErrMsg: .asciz "Failed to open input file \n"
EndOfFileMsg:    .asciz   "End of file reached\n"
ColonSpace:     .asciz": "
Space:		.asciz " " @ A space, so that the first "5" is properly alligned
NL:          .asciz   "\n "        @ new line 
Message1:    .asciz   "1337 Int Reading Sum Tracker \n"
.end