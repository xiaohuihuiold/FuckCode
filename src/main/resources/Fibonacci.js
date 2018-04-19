.fun void main void
    .mov v1 0
    .logic v2 20 > v1
    .mov v6 1
    .mov v7 1
    .while v2

        .data v4 v6 + v7
        .mov v6 v7
        .mov v7 v4
        .data v5 v4 + ","
        .inv print v5

        .data v1 v1 + 1
        .logic v2 20 > v1
    .end-while
.end-fun main