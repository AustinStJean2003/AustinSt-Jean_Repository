In my development projects 2 course I created a fitness app for the school with a team. This is a simulated real world project, working with a client working in sprints.
For this project we used **Typescript**, **Tailwind**, and **C#** for the backend.
Here is an example what something I created within the system. It is a BMI calculator that a student could use to keep track of their BMI as their results would be displayed in a graph using a library called **Victory**.
Only their past 5 results are recorded and the date the did their calculation is recorded. Users could not upload more than one calculation in the same day. 

***CODE SNIPPET***

```ts
import Camera from "@/components/CameraButton";
import FontedText from "@/components/FontedText";
import PageLayout from "@/components/PageLayout";
import RepositoryContext from "@/components/RepositoryContext";
import tw from "@/lib/tailwind";
import { useIdentity } from "@/util/useIdentity";
import { useQuery } from "@tanstack/react-query";
import { router } from "expo-router";
import React, { FC, useContext } from "react";
import { ImageBackground, View } from "react-native";
import { TouchableOpacity } from "react-native-gesture-handler";
import * as V from "victory";

export const Account: FC = () => {
	const { repository, authService } = useContext(RepositoryContext);
	const { isLoading: isMeLoading, data: me } = useIdentity(authService);

	const { data: BMIData, isLoading: isLoadingBMIData } = useQuery({
		queryKey: [`BMIData`],
		queryFn: () => repository.getBmiByStudentId(me.student.studentId),
		enabled: !isMeLoading && !!me,
	});

	let formattedData = [];

	if (!isLoadingBMIData && BMIData) {
		formattedData = BMIData?.map((data) => {
			const date = new Date(data.date);
			const month = date
				.toLocaleString("default", { month: "long" })
				.substring(0, 3)
				.toUpperCase();
			const dayOfMonth = date.getDate();
			const dateEntry = month + dayOfMonth;
			return { x: dateEntry, y: data.value };
		});

		if (formattedData.length === 1) {
			formattedData.unshift({ x: " ", y: 0 });
		}
		formattedData = formattedData.slice(-5);
	}

	const minY = 0;
	const maxY = 30;

	const DEFAULT_PROFILE_PICTURE = require("@/assets/DEFAULT_PICTURE.png");

	return (
		<PageLayout
			title="Profile"
			logout={true}
			helpRoute="/students/profile/documentation/">
			<View style={tw`flex h-30 pt-5`}>
				{!isMeLoading && me ? (
					<ImageBackground
						source={
							me.student.uploadId
								? {
										uri: repository.getFileUrl(me.student.uploadId),
									}
								: DEFAULT_PROFILE_PICTURE
						}
						imageStyle={tw`rounded-full h-30 w-30 mx-auto`}
						accessibilityLabel="Profile Picture"
					/>
				) : (
					<></>
				)}
			</View>
			<View style={tw`flex items-center pt-10`}>
				{!isMeLoading && me ? (
					<FontedText fontName={"Quicksand"} style={tw`text-2xl font-bold`}>
						{me.name}
					</FontedText>
				) : (
					<></>
				)}
			</View>
			<View style={tw`ml-5`}>
				{!isLoadingBMIData ? (
					<V.VictoryChart theme={V.VictoryTheme.material}>
						<V.VictoryLine
							style={{
								data: { stroke: "#6c4cbd" },
								parent: { border: "1px solid #ccc" },
							}}
							data={formattedData}
						/>
						<V.VictoryAxis tickFormat={(t) => t} />
						<V.VictoryAxis
							dependentAxis
							domain={[minY, maxY]}
							tickFormat={(t) => t.toFixed(2)}
						/>
					</V.VictoryChart>
				) : (
					<></>
				)}
			</View>
			<TouchableOpacity
				onPress={() => router.push("/students/profile/add-bmi")}>
				<FontedText
					fontName="Quicksand"
					style={tw`w-40 h-40 text-center pt-15 rounded-full m-auto bg-card-background shadow-lg text-primary-text text-xl font-bold`}>
					Add BMI
				</FontedText>
			</TouchableOpacity>
			<View style={tw`flex items-center h-50 justify-end`}>
				<Camera Press={() => router.push("/students/profile/add-picture")} />
			</View>
		</PageLayout>
	);
};

export default Account;
```
***Example of the graph in the application***<br/>
![image](https://github.com/HeritageCollegeClassroom/2020-program-exit-assessment-AustinStJean2003/assets/75050349/9fd7df87-1ec8-477d-b7f0-7631d3be86e2)



***Azure Task and User Story***
![image](https://github.com/HeritageCollegeClassroom/2020-program-exit-assessment-AustinStJean2003/assets/75050349/f1abeba1-790a-4ae9-86bd-4b6820b0a3e2)

