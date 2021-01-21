import tensorflow as tf

tf.reset_default_graph()
gr = tf.Graph()
with gr.as_default():
  a = tf.Variable(2, name='a')
  b = tf.Variable(3, name='b')
  total = tf.math.add(a, b, name='total')
  saver = tf.train.Saver()
  sess = tf.Session()
  sess.run(tf.global_variables_initializer())
  sess.run(total)
  saver.save(sess, './model/saved_model')

tf.train.write_graph(graph_or_graph_def=gr, logdir='C://Users//kumchand0//Apps//sts-workspace//tensorflow-java//model//a', name='saved_model.pbtxt')
tf.train.write_graph(gr, 'C://Users//kumchand0//Apps//sts-workspace//tensorflow-java//model//a', 'saved_model.pb', as_text=False)


tf.reset_default_graph()
gr = tf.Graph()
builder = tf.saved_model.builder.SavedModelBuilder('C://Users//kumchand0//Apps//sts-workspace//tensorflow-java//model//b')
with gr.as_default():
  a = tf.Variable(7, name='a')
  b = tf.Variable(8, name='b')
  total = tf.math.add(a, b, name='total')
  sess = tf.Session()
  sess.run(tf.global_variables_initializer())
  sess.run(total)
  builder.add_meta_graph_and_variables(sess, [tf.saved_model.tag_constants.SERVING])
  builder.save()


tf.reset_default_graph()
with tf.Session() as sess:    
  saver = tf.train.import_meta_graph('./model/saved_model.meta')
  saver.restore(sess,tf.train.latest_checkpoint('./model'))
  sess.run(tf.global_variables_initializer())
  print(sess.run("total:0"))