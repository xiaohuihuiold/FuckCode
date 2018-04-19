.line 1
.fun void main void
    .line 2
    .mov v1 20
    .line 3
    .logic v2 30 > v1
    .line 4
    .if v2
        .line 5
        .inv println "30大于20"
    .end-if
    .line 7
    .if !v2
        .line 8
        .inv println "30小于20"
    .end-if
    .while v2
    .data v1 v1 + 1
    .inv println v1
    .logic v2 30 > v1
    .end-while
    .line 10
.end-fun main