In advanced topics 2 I did an assignment where I used pandas and matplotlib to visualize data from a CSV file. In this example I created a bar graph of the highest estimates by age group.

```py
import matplotlib.pyplot as plt
sortedmaletop10 = maledf.sort_values(by="AGE", ascending=False)
fig = plt.figure()
ax = fig.add_axes([0.1,0.1,1,1])
ax.barh(sortedmaletop10['AGE'], sortedmaletop10['ESTIMATE'], color="blue")
plt.ylabel('AGE')
plt.xlabel('ESTIMATE')
plt.title("Top Entries By Age for Males")
plt.show()
```
![image](https://github.com/HeritageCollegeClassroom/2020-program-exit-assessment-AustinStJean2003/assets/75050349/6065a5d8-bbf2-4f34-8e8d-4ecaa600b9c9)
