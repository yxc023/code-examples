import java.util.regex.Matcher
import java.util.regex.Pattern

def p = ~/foo/
assert p instanceof Pattern

println p.class


def text = "some text to match"
def m = text =~ /match/
assert m instanceof Matcher
if (!m) {
    throw new RuntimeException("Oops, text not found!")
}
println m
println m.group()
println m.dump()