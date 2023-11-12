# grouping-demo
This is a simple POC to try grouping duplicates, where the groupings are
defined by two attributes: one in a parent class, and one in a child class.
In addition, the parent class is one of possibly multiple such elements in a List
within ITS parent class. So, duplicates may be found across the top-level
List, but need to go down to the next child level to get all needed
attributes for the grouping. This combines multiple Stream and
Collector operations to get the desired result.
