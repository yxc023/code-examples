import groovy.transform.TypeChecked

class BodySpec {
    void p(String p) {
        println "Content: ${p}"
    }
}

class EmailSpec {
    void from(String from) { println "From: $from" }

    void to(String... to) { println "To: $to" }

    void subject(String subject) { println "Subject: $subject" }

    void body(@DelegatesTo(value = BodySpec, strategy = Closure.DELEGATE_ONLY) Closure body) {
        def bodySpec = new BodySpec()
        def code = body.rehydrate(bodySpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
    }

    void send() {
        println "send"
    }
}


def email(@DelegatesTo(value = EmailSpec, strategy = Closure.DELEGATE_ONLY) Closure cl) {
    def email = new EmailSpec()
    def code = cl.rehydrate(email, this, this)
    code.resolveStrategy = Closure.DELEGATE_ONLY
    code()
}

@TypeChecked
void sendEmail() {
    email {

        from 'dsl-guru@mycompany.com'
        to 'john.doe@waitaminute.com'
        subject 'The pope has resigned!'
        body {
            p 'Really, the pope has resigned!'
        }
    }
}


class DelegateObj {
    void foo(String s) {
        println "delegateObj ${s}"
    }
}

def strategy(Closure closure) {

    code = closure.rehydrate(new DelegateObj(), this, this)
    code.resolveStrategy = Closure.OWNER_FIRST
    code()
}

strategy {
    foo "fuck"
}


def exec(@DelegatesTo.Target Object target, @DelegatesTo Closure code) {
    def clone = code.rehydrate(target, this, this)
    clone()
}

def email = new EmailSpec()
exec(email) {
    from '...'
    to '...'
    send()
}


public <T> void configure(
        @DelegatesTo.Target List<T> elements,
        @DelegatesTo(strategy = Closure.DELEGATE_FIRST, genericTypeIndex = 0) Closure configuration) {
    elements.each { e ->
        def clone = configuration.rehydrate(e, this, this)
        clone.resolveStrategy = Closure.DELEGATE_FIRST
        clone.call()
    }
}

@groovy.transform.ToString
class Realm {
    String name
}

List<Realm> list = []
3.times { list << new Realm() }
configure(list) {
    name = 'My Realm'

}

println list
assert list.every { it.name == 'My Realm' }



class Mapper<T,U> {
    final T value
    Mapper(T value) { this.value = value }
    U map(@DelegatesTo(type ="T") Closure<U> producer) {
        producer.delegate = value
        producer()
    }
}


@TypeChecked
def ff() {
    def mapper = new Mapper<String,Integer>('Hello')

    println mapper.map {
        length()
    }
}