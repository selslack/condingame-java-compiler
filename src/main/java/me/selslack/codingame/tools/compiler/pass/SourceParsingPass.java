package me.selslack.codingame.tools.compiler.pass;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import javaslang.collection.HashSet;
import javaslang.collection.List;
import javaslang.collection.Set;
import me.selslack.codingame.tools.compiler.Type;

import java.io.File;
import java.util.Optional;

public class SourceParsingPass implements CompilerPass<List<File>, Set<Type>> {
    @Override
    public Set<Type> process(List<File> input) throws Exception {
        Set<Type> context = HashSet.empty();

        for (File source : input) {
            CompilationUnit unit = JavaParser.parse(source, null, false);
            PackageDeclaration pkg = unit.getPackage();
            List<ImportDeclaration> imports = List.ofAll(unit.getImports());

            for (TypeDeclaration type : unit.getTypes()) {
                context = context.add(new Type(Optional.ofNullable(pkg), imports, type));
            }
        }

        return context;
    }
}
