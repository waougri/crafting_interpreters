from io import  TextIOWrapper
from sys import argv

def define_ast(output_dir: str, class_name: str, subclasses: dict[str, str]):
    path: str = output_dir + "/" + class_name + ".java"
    
    with open(file = path, mode = "w", encoding="utf-8") as writer:
        _= writer.write("package io.github.waougri;")
        _= writer.write("\n")
        _= writer.write("import java.util.List;")
        _= writer.write("\n")
        _= writer.write(f"abstract class {class_name} {{\n")
        for metaclass, metafields in subclasses.items() :
            fields: list[str] = metafields.strip().split(',')

            _= writer.write(f"public static class {metaclass} extends {class_name} {{")
            _= writer.write("\n")
            _= writer.write(f"{metaclass} ({metafields}) {{")
            _= writer.write("\n")
            for field in fields:
                field_type, field_name = field.strip().split(' ');
                _= writer.write(f"this.{field_name} = {field_name};")
                _= writer.write("\n")
            _= writer.write("}\n")
            _= writer.write("@Override")
            _= writer.write("<R> R accept(Visitor<R> visitor) {\n")
            _= writer.write(f"return visitor.visit{metaclass}{class_name}(this);")
            _= writer.write("}\n")
            
            for field in fields:
                field_type, field_name = field.strip().split(' ');
                _= writer.write(f"final {field_type} {field_name};")
                _= writer.write("\n")
            _= writer.write("}\n")
            
        _= writer.write("abstract <R> R accept(Visitor<R> visitor);")
        define_visitor(writer, class_name, subclasses)
        _= writer.write("}\n")


def define_visitor(writer: TextIOWrapper,  class_name: str, subclasses: dict[str, str] ): 
    _=writer.write("interface Visitor<R> {\n");
    for type_name in subclasses.keys():
        _=writer.write(f"  R visit{type_name}{class_name}({type_name} {class_name.lower()}); \n")

    _= writer.write("}\n");
    

def main():
    if(len(argv) != 2):
        print("Usage: python generate_ast.py <out_dir>");
        exit(64);
    OUTPUT_DIR: str = argv[1]
    
    subclasses = {
        "Binary": "Expr left, Token operator, Expr right",
        "Grouping": "Expr expression",
        "Literal": "Object value",
        "Unary": "Token operator, Expr right"
    }

    define_ast(OUTPUT_DIR, "Expr", subclasses);

    pass



if __name__ == "__main__":
    main()
