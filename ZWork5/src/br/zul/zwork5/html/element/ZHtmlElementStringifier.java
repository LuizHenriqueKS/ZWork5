package br.zul.zwork5.html.element;

/**
 *
 * @author luizh
 */
class ZHtmlElementStringifier {
     
    //==========================================================================
    //VARIÁVEIS
    //==========================================================================
    private final ZHtmlElement element;
    
    //==========================================================================
    //CONSTRUTORES
    //==========================================================================
    public ZHtmlElementStringifier(ZHtmlElement element) {
        this.element = element;
    }
    
    //==========================================================================
    //MÉTODOS PÚBLICOS
    //==========================================================================
    public String stringify() {
        StringBuilder builder = new StringBuilder();
        if (hasContent()){
            appendOpen(builder);
            appendContent(builder);
            appendClose(builder);
        } else {
            appendBasic(builder);
        }
        return builder.toString();
    }
    
    //==========================================================================
    //MÉTODOS PRIVADOS
    //==========================================================================
    private boolean hasContent(){
        return element.streamNodes().findAny().isPresent();
    }
    
    private void appendBasic(StringBuilder builder){
        builder.append("<").append(element.getTagName());
        appendAttrs(builder);
        builder.append("/>");
    }
    
    private void appendOpen(StringBuilder builder){
        builder.append("<");
        builder.append(element.getTagName());
        appendAttrs(builder);
        builder.append(">");
    }
    
    private void appendAttrs(StringBuilder builder){
        if (!element.attributeMap().isEmpty()){
            StringBuilder attrBuilder = new StringBuilder();
            appendOnlyAttrs(attrBuilder);
            String attrs = attrBuilder.toString().trim();
            if (!attrs.isEmpty()){
                builder.append(" ").append(attrs);
            }
        }
    }
    
    private void appendContent(StringBuilder builder){
        element.streamNodes().map(node->node.toString())
                             .forEach(str->builder.append(str));
    }
    
    private void appendClose(StringBuilder builder){
        builder.append("</");
        builder.append(element.getTagName());
        builder.append(">");
    }

    private void appendOnlyAttrs(StringBuilder builder) {
        element.attributeMap().forEach((key, value)->{
            builder.append(" ");
            appendAttr(builder, key, value);
        });
    }
    
    private void appendAttr(StringBuilder builder, String name, String value){
        if (value==null){
            
        } else if (value.isEmpty()){
            builder.append(name);
        } else {
            builder.append(name);
            builder.append("=\"");
            builder.append(value);
            builder.append("\"");
        }
    }
    
}
