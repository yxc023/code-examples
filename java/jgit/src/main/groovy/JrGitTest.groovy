import org.ajoberstar.grgit.Commit
import org.ajoberstar.grgit.Grgit
import org.ajoberstar.grgit.exception.GrgitException

/**
 * @author yangxiaochen
 * @date 2017/8/13 00:26
 */
class JrGitTest {
    public static void main(String[] args) {
        def grgit
        try {
            grgit = Grgit.open(dir: 'build/git')

        } catch (GrgitException e) {
            grgit = Grgit.clone(dir: 'build/git', uri: 'https://github.com/ajoberstar/test-repo.git')
        }

        // no arg variant, only applies to operations that don't require input
        List<Commit> commits = grgit.log()



// map argument variant
//        grgit.log(includes: ['master'], excludes: ['old-stuff'], skipCommits: 5)

// closure argument variant
//        grgit.log {
//            range('1.0.0', '1.5.1')
//            maxCommits = 2
//        }
    }
}
