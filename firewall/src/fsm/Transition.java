package fsm;

public interface Transition {

}

class PageDefault implements Transition{}

class PageLogin implements Transition{}
class PageAddRule implements Transition{}
class PageDeleteRule implements Transition{}
class PageChangeRule implements Transition{}
class PageWait implements Transition{}
