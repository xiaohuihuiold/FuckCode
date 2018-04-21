.fun void main void
    .logic vrun 4 > 3
    .while vrun
        .inv sleep 1000
        .inv $java.lang.System currentTimeMillis vtime void void
        .inv println vtime
    .end-while
.end-fun main