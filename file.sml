    mov EAX 6
    mov EBX 1
    mov ECX 1
    add EBX ECX
    div EAX EBX
f3: mul EBX EAX
    sub EAX ECX
    jnz EAX f3
    out EBX