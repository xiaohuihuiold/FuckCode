.line 1
.fun void main void
    .line 2
    .mov v1 0
    .line 3
    .logic v2 10 > v1
    .line 4
    .while v2
        .line 5
        .mov v3 v1
        .line 6
        .logic v4 10 > v3
        .line 7
        .while v4
            .line 8
            .inv print "#"
            .line 9
            .data v3 v3 + 1
            .line 10
            .logic v4 10 > v3
        .end-while
        .line 11
        .inv println ""
        .line 12
        .data v1 v1 + 1
        .line 13
        .logic v2 10 > v1
    .end-while
.end-fun main