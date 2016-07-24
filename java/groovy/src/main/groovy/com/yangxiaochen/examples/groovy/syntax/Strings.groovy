assert 'ab' == 'a' + 'b'

println "========"
def sql = '''
select *
from table
where id = 1'''
println sql

def sql2 = '''select *
from table
where id = 1'''
println sql2


def sql3 = '''\
select *
from table
where id = 1'''
println sql3


