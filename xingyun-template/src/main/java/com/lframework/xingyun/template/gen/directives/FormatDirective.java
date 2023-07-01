package com.lframework.xingyun.template.gen.directives;

import com.lframework.starter.common.utils.CollectionUtil;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FormatDirective implements TemplateDirectiveModel {

  public static final String DIRECTIVE_NAME = "format";

  private static final char NEWLINE = '\n';

  @Override
  public void execute(Environment env, @SuppressWarnings("rawtypes") Map params,
      TemplateModel[] loopVars,
      TemplateDirectiveBody body) throws TemplateException, IOException {

    FormatWriter writer = new FormatWriter(env.getOut(),
        env.getCurrentDirectiveCallPlace().getBeginColumn());
    body.render(writer);

    List<Character> chars = writer.getWriteChars();
    char[] space = writer.getSpace();

    if (!CollectionUtil.isEmpty(chars)) {
      List<Character> checkList = chars.subList(chars.size() - space.length, chars.size());
      if (checkList.stream().allMatch(t -> t == ' ')) {
        chars = chars.subList(0, chars.size() - space.length);
      }

      char[] newChars = new char[chars.size()];
      for (int i = 0; i < chars.size(); i++) {
        newChars[i] = chars.get(i);
      }
      env.getOut().write(newChars);
    }
  }

  private static class FormatWriter extends Writer {

    private final Writer out;

    private final char[] space;
    private final List<Character> writeChars = new ArrayList<>();
    private boolean newLine = true;

    public FormatWriter(Writer out, int column) {

      this.out = out;
      this.space = new char[column];
      Arrays.fill(this.space, ' ');
    }

    public char[] getSpace() {

      return space;
    }

    public List<Character> getWriteChars() {

      return writeChars;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

      List<Character> chars = new ArrayList<>();
      for (int i = off; i < len; i++) {
        char c = cbuf[i];
        if (newLine) {
          for (char s : space) {
            chars.add(s);
          }
          newLine = false;
        }

        if (c == NEWLINE) {
          newLine = true;
        }

        chars.add(c);

        if (newLine) {
          for (char s : space) {
            chars.add(s);
          }
          newLine = false;
        }
      }

      writeChars.addAll(chars);
    }

    @Override
    public void flush() throws IOException {

      out.flush();
    }

    @Override
    public void close() throws IOException {

      out.close();
    }

  }

}
