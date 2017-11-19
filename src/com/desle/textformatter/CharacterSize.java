package com.desle.textformatter;

public enum CharacterSize {
    A('A', 3),
    a('a', 3),
    B('B', 3),
    b('b', 3),
    C('C', 3),
    c('c', 3),
    D('D', 3),
    d('d', 3),
    E('E', 3),
    e('e', 3),
    F('F', 3),
    f('f', 2.5),
    G('G', 3),
    g('g', 3),
    H('H', 3),
    h('h', 3),
    I('I', 2),
    i('i', 1),
    J('J', 3),
    j('j', 3),
    K('K', 3),
    k('k', 2.5),
    L('L', 3),
    l('l', 1.5),
    M('M', 3),
    m('m', 3),
    N('N', 3),
    n('n', 3),
    O('O', 3),
    o('o', 3),
    P('P', 3),
    p('p', 3),
    Q('Q', 3),
    q('q', 3),
    R('R', 3),
    r('r', 3),
    S('S', 3),
    s('s', 3),
    T('T', 3),
    t('t', 2),
    U('U', 3),
    u('u', 3),
    V('V', 3),
    v('v', 3),
    W('W', 3),
    w('w', 3),
    X('X', 3),
    x('x', 3),
    Y('Y', 3),
    y('y', 3),
    Z('Z', 3),
    z('z', 3),
    NUM_1('1', 3),
    NUM_2('2', 3),
    NUM_3('3', 3),
    NUM_4('4', 3),
    NUM_5('5', 3),
    NUM_6('6', 3),
    NUM_7('7', 3),
    NUM_8('8', 3),
    NUM_9('9', 3),
    NUM_0('0', 3),
    EXCLAMATION_POINT('!', 1),
    AT_SYMBOL('@', 3.5),
    NUM_SIGN('#', 3),
    DOLLAR_SIGN('$', 3),
    PERCENT('%', 3),
    UP_ARROW('^', 3),
    AMPERSAND('&', 3),
    ASTERISK('*', 2.5),
    LEFT_PARENTHESIS('(', 2.5),
    RIGHT_PERENTHESIS(')', 2.5),
    MINUS('-', 3),
    UNDERSCORE('_', 3),
    PLUS_SIGN('+', 3),
    EQUALS_SIGN('=', 3),
    LEFT_CURL_BRACE('{', 2.5),
    RIGHT_CURL_BRACE('}', 2.5),
    LEFT_BRACKET('[', 2),
    RIGHT_BRACKET(']', 2),
    COLON(':', 1),
    SEMI_COLON(';', 1),
    DOUBLE_QUOTE('"', 2.5),
    SINGLE_QUOTE('\'', 1.5),
    LEFT_ARROW('<', 2.5),
    RIGHT_ARROW('>', 2.5),
    QUESTION_MARK('?', 3),
    SLASH('/', 3),
    BACK_SLASH('\\', 3),
    LINE('|', 1),
    TILDE('~', 3.5),
    TICK('`', 1.5),
    PERIOD('.', 1),
    COMMA(',', 1),
    SPACE(' ', 2),
    UTF_1('⥼', 2),
    UTF_2('⥽', 2),
    UTF_3('⟝', 4),
    UTF_4('⟞', 4),
    UTF_5('⋙', 2),
    UTF_6('⋘', 2),
    UTF_7('⁕', 2),
    UTF_8('᚛', 4),
    UTF_9('᚜', 4),
    UTF_10('☠', 2.5),
    UTF_11('✕', 3),
    UTF_12('✔', 4),
    DEFAULT('a', 3);
    
    private char character;
    private double size;
    
    CharacterSize(char character, double size) {
        this.character = character;
        this.size = size;
    }
    
    public char getCharacter(){
        return this.character;
    }
    
    public double getSize(){
        return this.size;
    }
    
    public double getBoldSize(){
        if(this == CharacterSize.SPACE) return this.getSize();
        return (this.size + 1);
    }
    
    public static CharacterSize getCharacterSize(char c){
        for(CharacterSize dFI : CharacterSize.values()){
            if(dFI.getCharacter() == c) return dFI;
        }
        return CharacterSize.DEFAULT;
    }
}